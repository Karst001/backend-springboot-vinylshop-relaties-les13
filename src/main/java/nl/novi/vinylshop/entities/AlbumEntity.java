package nl.novi.vinylshop.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "albums")
public class AlbumEntity extends BaseEntity {

    private String title;
    private int releaseYear;

    // Many albums -> one publisher
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    // Many albums -> one genre
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    // One album -> many stock items
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockEntity> stockItems = new ArrayList<>();

    // Many albums <-> many artists
    @ManyToMany
    @JoinTable(
            name = "albums_artists",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<ArtistEntity> artists = new HashSet<>();

    public AlbumEntity() {}

    //helper methods for ManyToMany
    public void addArtist(ArtistEntity artist) {
        this.artists.add(artist);
        artist.getAlbums().add(this);
    }

    public void removeArtist(ArtistEntity artist) {
        this.artists.remove(artist);
        artist.getAlbums().remove(this);
    }

    //getters/setters

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

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }

    public List<StockEntity> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<StockEntity> stockItems) {
        this.stockItems = stockItems;
    }

    public Set<ArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(Set<ArtistEntity> artists) {
        this.artists = artists;
    }
}


