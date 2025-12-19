package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.novi.vinylshop.dtos.publisher.PublisherRequestDTO;
import nl.novi.vinylshop.dtos.publisher.PublisherResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.PublisherEntity;
import nl.novi.vinylshop.mappers.PublisherDTOMapper;
import nl.novi.vinylshop.repositories.AlbumRepository;
import nl.novi.vinylshop.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherDTOMapper publisherDTOMapper;
    private final AlbumRepository albumRepository;

    public PublisherService(PublisherRepository publisherRepository, PublisherDTOMapper publisherDTOMapper, AlbumRepository albumRepository) {
        this.publisherRepository = publisherRepository;
        this.publisherDTOMapper = publisherDTOMapper;
        this.albumRepository = albumRepository;
    }


    public List<PublisherResponseDTO> findAllPublishers() {
        //return the GenreResponseDTO via the DTO mapper to the controller
        return publisherDTOMapper.mapToDto(publisherRepository.findAll());
    }


    public PublisherResponseDTO findPublisherById(Long id) {
        //check if records exists
        PublisherEntity result = getPublisherEntityById(id);   //this step can be skipped when using stored procedures

        if (result != null) {
            return publisherDTOMapper.mapToDto(result);
        }
        return null;
    }



    public PublisherResponseDTO createPublisher(PublisherRequestDTO publisherDTO) {
        //map the genreDTO to the genreEntity
        PublisherEntity result = publisherDTOMapper.mapToEntity(publisherDTO);

        //call the repo and save the result
        result = publisherRepository.save(result);

        //return the publisherEntity via the DTO mapper to the controller
        return publisherDTOMapper.mapToDto(result);
    }


    public PublisherResponseDTO updatePublisher(Long id, PublisherRequestDTO requestDto) {
        //check if records exists
        PublisherEntity result = getPublisherEntityById(id);  //this step can be skipped when using stored procedures

        //update values
        result.setName(requestDto.getName());
        result.setEmailAddress(requestDto.getEmailAddress());
        result.setContactDetails(requestDto.getContactDetails());

        //save to database
        result = publisherRepository.save(result);

        //return entity to controller via de DTO
        return publisherDTOMapper.mapToDto(result);
    }


    @Transactional
    public void deletePublisher(Long id) {
        //get the publisher by id
        PublisherEntity publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher " + id + " not found"));

        // remove the relation, albums.publisher_id must be null first before you can delete the publisher from the album
        if (publisher.getAlbums() != null && !publisher.getAlbums().isEmpty()) {
            for (AlbumEntity album : publisher.getAlbums()) {
                album.setPublisher(null);
                albumRepository.save(album);
            }
        }

        publisherRepository.delete(publisher);
    }

    //private function to check if entity exists
    private PublisherEntity getPublisherEntityById(Long id) {
        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(id);

        //check if Genre is present, if not return null
        return publisherEntity.orElse(null);

        //or write as
        //        if(publisherEntity.isPresent()){
        //            return publisherEntity.get();
        //        } else {
        //            return null;
        //        }
    }
}
