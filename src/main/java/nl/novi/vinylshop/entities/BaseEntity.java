package nl.novi.vinylshop.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    //define the properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDate;

    @Column(name = "edited_date")
    private LocalDateTime editedDate;

    //event on Create, Hibernate scans lifecycle hooks inside entities
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.editedDate = LocalDateTime.now();
    }

    //event on Update, Hibernate scans lifecycle hooks inside entities
    @PreUpdate
    protected void onUpdate() {
        this.editedDate = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateDate() {
        return createdDate;
    }

    public LocalDateTime getEditedDate() {
        return editedDate;
    }
}

