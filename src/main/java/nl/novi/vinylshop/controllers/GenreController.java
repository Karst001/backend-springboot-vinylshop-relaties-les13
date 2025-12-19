package nl.novi.vinylshop.controllers;

import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.genre.GenreRequestDTO;
import nl.novi.vinylshop.dtos.genre.GenreResponseDTO;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.services.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;
    private final UrlHelper urlHelper;

    public GenreController(GenreService genreService, UrlHelper urlHelper) {
        this.genreService = genreService;
        this.urlHelper = urlHelper;
    }

    //mappings
    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> findAllGenres() {
        //load the DTO with all genres via the service
        List<GenreResponseDTO> genres = genreService.findAllGenres();

        //return the List
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> findGenreById(@PathVariable Long id) {
        //load the DTO with one genre via the service
        GenreResponseDTO genre = genreService.findGenreById(id);

        //return the found object or null
        if (genre != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(genre, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreRequestDTO genreInput) {
        //@Valid triggers the validation process before data is processed the service layer

        //create a genre via the service and store result in ResponseDTO
        GenreResponseDTO newGenre = genreService.createGenre(genreInput);

        //return the new response and add Id to header
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newGenre.getId())).body(newGenre);
    }


    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreRequestDTO genreInput) {
        //@Valid triggers the validation process before data is processed the service layer

        //update genre via the service and store result in ResponseDTO
        GenreResponseDTO updated = genreService.updateGenre(id, genreInput);

        //return the updated object or null
        if (updated != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(updated, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


