package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDTO;
import nl.novi.vinylshop.dtos.album.AlbumRequestDTO;
import nl.novi.vinylshop.dtos.album.AlbumResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.entities.GenreEntity;
import nl.novi.vinylshop.entities.PublisherEntity;
import nl.novi.vinylshop.mappers.AlbumDTOMapper;
import nl.novi.vinylshop.mappers.AlbumExtendedDTOMapper;
import nl.novi.vinylshop.repositories.AlbumRepository;
import nl.novi.vinylshop.repositories.ArtistRepository;
import nl.novi.vinylshop.repositories.GenreRepository;
import nl.novi.vinylshop.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumDTOMapper albumDTOMapper;
    private final AlbumExtendedDTOMapper albumExtendedDTOMapper;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;

    public AlbumService(AlbumRepository albumRepository, AlbumDTOMapper albumDTOMapper,
                        GenreRepository genreRepository, PublisherRepository publisherRepository,
                        AlbumExtendedDTOMapper albumExtendedDTOMapper,
                        ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.albumDTOMapper = albumDTOMapper;
        this.genreRepository = genreRepository;
        this.publisherRepository = publisherRepository;
        this.albumExtendedDTOMapper = albumExtendedDTOMapper;
        this.artistRepository = artistRepository;
    }

    public List<AlbumResponseDTO> findAllAlbums() {
        //return the AlbumResponseDTO via the DTO mapper to the controller
        return albumDTOMapper.mapToDto(albumRepository.findAll());
    }

    public AlbumExtendedResponseDTO findAlbumById(Long id) {
        //check if records exists
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album " + id + " not found"));

        //return the entity via the mapper
        return albumExtendedDTOMapper.mapToDto(album);
    }


    public AlbumResponseDTO updateAlbum(Long id, AlbumRequestDTO requestDto) {
        //check if records exists
        AlbumEntity result = getAlbumEntityById(id);  //this step can be skipped when using stored procedures

        //update values
        result.setTitle(requestDto.getTitle());
        result.setReleaseYear(requestDto.getReleaseYear());

        //save to database
        result = albumRepository.save(result);

        //return entity to controller via de DTO
        return albumDTOMapper.mapToDto(result);
    }


    private AlbumEntity getAlbumEntityById(long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album " + albumId + " not found"));
    }

    private ArtistEntity getArtistEntity(long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist " + artistId + " not found"));
    }


    public AlbumEntity createAlbum(AlbumRequestDTO albumDTO) {
        AlbumEntity album = new AlbumEntity();

        album.setTitle(albumDTO.getTitle());
        album.setReleaseYear(albumDTO.getReleaseYear());

        // set relations only if IDs are present
        if (albumDTO.getGenreId() != null) {
            album.setGenre(getGenreEntity(albumDTO.getGenreId()));
        }

        if (albumDTO.getPublisherId() != null) {
            album.setPublisher(getPublisherEntity(albumDTO.getPublisherId()));
        }

        return albumRepository.save(album);
    }

    private GenreEntity getGenreEntity(long genreId) {
        return genreRepository.findById(genreId)
            .orElseThrow(() -> new EntityNotFoundException("Genre " + genreId + " not found"));
    }

    private PublisherEntity getPublisherEntity(long publisherId) {
        return publisherRepository.findById(publisherId)
            .orElseThrow(() -> new EntityNotFoundException("Publisher " + publisherId + " not found"));
    }


    @Transactional  //It’s the safest way to ensure the persistence context tracks relationship changes properly, especially with LAZY collections.
    public void linkArtist(Long albumId, Long artistId) {
        AlbumEntity album = getAlbumEntityById(albumId);
        ArtistEntity artist = getArtistEntity(artistId);

        // Album is the owning side, so we update album.artists
        album.addArtist(artist);
        albumRepository.save(album);
    }

    @Transactional
    public void unlinkArtist(Long albumId, Long artistId) {
        AlbumEntity album = getAlbumEntityById(albumId);
        ArtistEntity artist = getArtistEntity(artistId);

        album.removeArtist(artist);
        albumRepository.save(album);
    }


    @Transactional
    public void deleteAlbum(Long id) {
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album " + id + " not found"));

        // disconnect many-to-many relation (albums_artists)
        if (album.getArtists() != null && !album.getArtists().isEmpty()) {
            //remove Artist from Album
            album.getArtists().forEach(artist -> artist.getAlbums().remove(album));
            album.getArtists().clear();
        }

        // handle the stock relation FK
        if (album.getStockItems() != null && !album.getStockItems().isEmpty()) {
            // orphanRemoval=true so this works
            album.getStockItems().clear();
        }

        // finally, do the delete
        albumRepository.delete(album);
    }


    public List<AlbumResponseDTO> getAlbumsWithStock(Boolean stock) {
        List<AlbumEntity> albums = albumRepository.findAll();

        List<AlbumEntity> filtered = albums.stream()
                .filter(album -> {
                    boolean hasStock = album.getStockItems() != null
                            && !album.getStockItems().isEmpty();
                    return stock ? hasStock : !hasStock;
                })
                .toList();

        return albumDTOMapper.mapToDto(filtered);
    }
}
