package nl.novi.vinylshop.controllers;

import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.publisher.PublisherRequestDTO;
import nl.novi.vinylshop.dtos.publisher.PublisherResponseDTO;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.services.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;
    private final UrlHelper urlHelper;

    public PublisherController(PublisherService publisherService, UrlHelper urlHelper) {
        this.publisherService = publisherService;
        this.urlHelper = urlHelper;
    }

    //mappings
    @GetMapping
    public ResponseEntity<List<PublisherResponseDTO>> findAllPublishers() {
        //load the DTO with all publishers via the service
        List<PublisherResponseDTO> publishers = publisherService.findAllPublishers();

        //return the list
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> findPublisherById(@PathVariable Long id) {
        //load the DTO with one publisher via the service
        PublisherResponseDTO publisher = publisherService.findPublisherById(id);

        //return the found object or null
        if (publisher != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        }
        return new ResponseEntity<>(publisher, HttpStatus.NO_CONTENT);
    }


    @PostMapping
    public ResponseEntity<PublisherResponseDTO> createPublisher(@Valid @RequestBody PublisherRequestDTO publisherInput) {
        //create a publisher via the service and store result in ResponseDTO
        PublisherResponseDTO newPublisher = publisherService.createPublisher(publisherInput);

        //return the new response and add Id to header
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newPublisher.getId())).body(newPublisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> updatePubliser(@PathVariable Long id, @Valid @RequestBody PublisherRequestDTO publisherInput) {
        //update publisher via the service and store result in ResponseDTO
        PublisherResponseDTO updated = publisherService.updatePublisher(id, publisherInput);

        //return the updated object or null
        if (updated != null) { //prepare proper response, provided id may not exist
            return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(updated, HttpStatus.NO_CONTENT);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
