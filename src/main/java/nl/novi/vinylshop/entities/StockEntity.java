package nl.novi.vinylshop.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class StockEntity extends BaseEntity {
    private String condition;
    private double price;

    //define relations: many Stock items belong to one Album
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")

    //add Album to Stock so values can be set
    private AlbumEntity album;

    //getters & setters Albums
    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    //constructor Stock Entity
    public StockEntity() {}

    public StockEntity(String condition, double price) {
        this.condition = condition;
        this.price = price;
    }

    //getters & setters Stock
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
