package nl.novi.vinylshop.controllers;

import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.artist.ArtistRequestDTO;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;
    private final UrlHelper urlHelper;

    public ArtistController(ArtistService artistService, UrlHelper urlHelper) {
        this.artistService = artistService;
        this.urlHelper = urlHelper;
    }

    //mappings
    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> findAllArtists() {
        //load the DTO with all genres via the service
        List<ArtistResponseDTO> artists = artistService.findAllArtists();

        //return the List
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> findArtistById(@PathVariable Long id) {
        //load the DTO with one artist via the service
        ArtistResponseDTO artist = artistService.findArtistById(id);

        //return the found object or null
        if (artist != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(artist, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<ArtistResponseDTO> createArtist(@Valid @RequestBody ArtistRequestDTO artistInput) {
        //@Valid triggers the validation process before data is processed the service layer

        //create a artist via the service and store result in ResponseDTO
        ArtistResponseDTO newArtist = artistService.createArtist(artistInput);

        //return the new response and add Id to header
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newArtist.getId())).body(newArtist);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> updateArtist(@PathVariable Long id, @Valid @RequestBody ArtistRequestDTO artistInput) {
        //@Valid triggers the validation process before data is processed the service layer

        //update artist via the service and store result in ResponseDTO
        ArtistResponseDTO updated = artistService.updateArtist(id, artistInput);

        //return the updated object or null
        if (updated != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(updated, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
