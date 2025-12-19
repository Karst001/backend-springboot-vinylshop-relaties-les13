package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.album.AlbumRequestDTO;
import nl.novi.vinylshop.dtos.album.AlbumResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlbumDTOMapper implements DTOMapperInterface<AlbumResponseDTO, AlbumRequestDTO, AlbumEntity> {

    // Dependency Injection
    private final GenreDTOMapper genreDTOMapper;
    private final PublisherDTOMapper publisherDTOMapper;
    private final ArtistDTOMapper artistDTOMapper;

    public AlbumDTOMapper(GenreDTOMapper genreDTOMapper,
                          PublisherDTOMapper publisherDTOMapper,
                          ArtistDTOMapper artistDTOMapper) {
        this.genreDTOMapper = genreDTOMapper;
        this.publisherDTOMapper = publisherDTOMapper;
        this.artistDTOMapper = artistDTOMapper;
    }

    //
    // Generic mapper method so we can reuse it for AlbumExtendedResponseDTO too.
    // Example:
    //  - mapToDto(model, new AlbumResponseDTO())
    //  - mapToDto(model, new AlbumExtendedResponseDTO())
    //
    public <D extends AlbumResponseDTO> D mapToDto(AlbumEntity model, D target) {
        // base fields
        target.setId(model.getId());
        target.setTitle(model.getTitle());
        target.setReleaseYear(model.getReleaseYear());

        // relations (only if they exist)
        if (model.getGenre() != null) {
            target.setGenre(genreDTOMapper.mapToDto(model.getGenre()));
        }

        if (model.getPublisher() != null) {
            target.setPublisher(publisherDTOMapper.mapToDto(model.getPublisher()));
        }

        if (model.getArtists() != null && !model.getArtists().isEmpty()) {
            target.setArtists(artistDTOMapper.mapToDto(new ArrayList<>(model.getArtists())));
        }

        return target;
    }

    @Override
    public AlbumResponseDTO mapToDto(AlbumEntity model) {
        return mapToDto(model, new AlbumResponseDTO());
    }

    @Override
    public List<AlbumResponseDTO> mapToDto(List<AlbumEntity> models) {
        List<AlbumResponseDTO> response = new ArrayList<>();

        for (AlbumEntity model : models) {
            response.add(mapToDto(model));
        }

        return response;
    }

    @Override
    public AlbumEntity mapToEntity(AlbumRequestDTO albumModel) {
        AlbumEntity entity = new AlbumEntity();

        entity.setTitle(albumModel.getTitle());
        entity.setReleaseYear(albumModel.getReleaseYear());

        // NOTE: relations (genre/publisher/artists) are usually set in the SERVICE
        // because you typically need repository lookups by id.

        return entity;
    }
}


