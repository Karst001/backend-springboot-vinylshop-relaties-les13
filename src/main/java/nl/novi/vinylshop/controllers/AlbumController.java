package nl.novi.vinylshop.controllers;

import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDTO;
import nl.novi.vinylshop.dtos.album.AlbumResponseDTO;
import nl.novi.vinylshop.dtos.album.AlbumRequestDTO;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.mappers.AlbumDTOMapper;
import nl.novi.vinylshop.services.AlbumService;
import nl.novi.vinylshop.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final AlbumDTOMapper albumDTOMapper;
    private final UrlHelper urlHelper;

    public AlbumController(AlbumService albumService, UrlHelper urlHelper, AlbumDTOMapper albumDTOMapper, ArtistService artistService) {
        this.albumService = albumService;
        this.urlHelper = urlHelper;
        this.albumDTOMapper = albumDTOMapper;
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> findAllAlbumsWithStock(@RequestParam(required = false) Boolean stock) {
        //in Postman
        //stock == null   -> no filter
        //stock == true   -> stockItems.size > 0
        //stock == false  -> stockItems.size == 0
        List<AlbumResponseDTO> albums;

        if (stock != null) {
            albums = albumService.getAlbumsWithStock(stock);
        } else {
            albums = albumService.findAllAlbums();
        }

        return ResponseEntity.ok(albums);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AlbumExtendedResponseDTO> findAlbumById(@PathVariable Long id) {
        //load the DTO with one album via the service
        AlbumExtendedResponseDTO album = albumService.findAlbumById(id);

        //return ResponseEntity.ok(album);
        if (album != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(album, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/artists")
    public ResponseEntity<List<ArtistResponseDTO>> getArtistsForAlbum(@PathVariable Long id){
        List<ArtistResponseDTO> artists = artistService.findArtistsForAlbum(id);
        return ResponseEntity.ok(artists);
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> createAlbum(
            @Valid @RequestBody AlbumRequestDTO albumInput) {

        AlbumEntity newAlbum = albumService.createAlbum(albumInput);

        AlbumResponseDTO response = albumDTOMapper.mapToDto(newAlbum);

        return ResponseEntity
                .created(urlHelper.getCurrentUrlWithId(newAlbum.getId()))
                .body(response);
    }

    @PostMapping("/{albumId}/artists/{artistId}")
    public ResponseEntity<Void> linkArtist(@PathVariable Long albumId, @PathVariable Long artistId) {
        albumService.linkArtist(albumId, artistId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{albumId}/artists/{artistId}")
    public ResponseEntity<Void> unlinkArtist(@PathVariable Long albumId, @PathVariable Long artistId) {
        albumService.unlinkArtist(albumId, artistId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumRequestDTO albumInput) {
        //@Valid triggers the validation process before data is processed the service layer

        //update genre via the service and store result in ResponseDTO
        AlbumResponseDTO updated = albumService.updateAlbum(id, albumInput);

        //return the updated object or null
        if (updated != null) {  //prepare proper response, provided id may not exist
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(updated, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
