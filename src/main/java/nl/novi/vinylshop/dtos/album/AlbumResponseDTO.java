package nl.novi.vinylshop.dtos.album;

import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.dtos.genre.GenreResponseDTO;
import nl.novi.vinylshop.dtos.publisher.PublisherResponseDTO;

import java.util.List;

public class AlbumResponseDTO {
    Long id;
    String title;
    int releaseYear;

    //set relations
    private GenreResponseDTO genre;
    private PublisherResponseDTO publisher;
    private List<ArtistResponseDTO> artists;

    // Getters en setters Album
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    //extra getters and setters for the relations
    public GenreResponseDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreResponseDTO genre) {
        this.genre = genre;
    }

    public PublisherResponseDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherResponseDTO publisher) {
        this.publisher = publisher;
    }

    public List<ArtistResponseDTO> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistResponseDTO> artists) {
        this.artists = artists;
    }
}
