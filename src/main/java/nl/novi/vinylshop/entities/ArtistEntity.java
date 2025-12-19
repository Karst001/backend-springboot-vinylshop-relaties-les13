package nl.novi.vinylshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "artists")
public class ArtistEntity extends BaseEntity {
    private String name;
    private String biography;

    //define relations: an Artists can have one of more Albums, Albums can have one or more Artists
    @ManyToMany(mappedBy = "artists")
    //define a set of Albums (not List<>) as List<> can have duplicates
    private Set<AlbumEntity> albums = new HashSet<>();

    public ArtistEntity() {}

    public ArtistEntity(String name, String biography) {
        this.name = name;
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    //getters/ setters for relations
    public Set<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<AlbumEntity> albums) {
        this.albums = albums;
    }
}
