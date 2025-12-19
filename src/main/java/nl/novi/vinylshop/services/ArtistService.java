package nl.novi.vinylshop.services;

import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.dtos.artist.ArtistRequestDTO;
import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.mappers.ArtistDTOMapper;
import nl.novi.vinylshop.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistDTOMapper artistDTOMapper;

    public ArtistService(ArtistRepository artistRepository, ArtistDTOMapper artistDTOMapper) {
        this.artistRepository = artistRepository;
        this.artistDTOMapper = artistDTOMapper;
    }

    public List<ArtistResponseDTO> findAllArtists() {
        //return the ArtistResponseDTO via the DTO mapper to the controller
        return artistDTOMapper.mapToDto(artistRepository.findAll());
    }

    public List<ArtistResponseDTO> findArtistsForAlbum(Long albumId) {
        List<ArtistEntity> artists = artistRepository.findArtistsByAlbumsId(albumId);

        return artistDTOMapper.mapToDto(artists);
    }

    public ArtistResponseDTO findArtistById(Long id) {
        //check if records exists
        ArtistEntity result = getArtistEntityById(id);  //this step can be skipped when using stored procedures

        //return the entity via the mapper
        if (result != null) {
            return artistDTOMapper.mapToDto(result);
        }
        return null;
    }

    public ArtistResponseDTO createArtist(ArtistRequestDTO artistDTO) {
        //map the artistDTO to the artistEntity
        ArtistEntity result = artistDTOMapper.mapToEntity(artistDTO);

        //call the repo and save the result
        result = artistRepository.save(result);

        //return the artistEntity via the DTO mapper to the controller
        return artistDTOMapper.mapToDto(result);
    }

    public ArtistResponseDTO updateArtist(Long id, ArtistRequestDTO requestDto) {
        //check if records exists
        ArtistEntity result = getArtistEntityById(id);  //this step can be skipped when using stored procedures

        //update values
        result.setName(requestDto.getName());
        result.setBiography(requestDto.getBiography());

        //save to database
        result = artistRepository.save(result);

        //return entity to controller via de DTO
        return artistDTOMapper.mapToDto(result);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }


    //private function to check if entity exists
    private ArtistEntity getArtistEntityById(Long id) {
        Optional<ArtistEntity> artistEntity = artistRepository.findById(id);

        //check if Artist is present, if not return null
        return artistEntity.orElse(null);
    }
}
