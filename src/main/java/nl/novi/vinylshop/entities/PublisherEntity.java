package nl.novi.vinylshop.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "publishers")
public class PublisherEntity extends BaseEntity{
    //define the properties
    @Column(nullable = false) //name column is mandatory
    private String name;
    private String emailAddress;
    private String contactDetails;

    //define relation: a Publisher can have 0 or more albums
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<AlbumEntity> albums;

    //getters and setters for Albums
    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumEntity> albums) {
        this.albums = albums;
    }


    //getters and setters for Publisher
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }
}
