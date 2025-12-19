package nl.novi.vinylshop.dtos.album;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

public class AlbumRequestDTO {
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title lenght must be between 3 and 100 characters")
    String title;

    @NotNull
    @Min(1877)
    @Max(2100)
    private int releaseYear;

    private Long genreId;
    private Long PublisherId;

    public String getTitle() {
        return title;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getPublisherId() {
        return PublisherId;
    }

    // Getters en setters Album
    public void setPublisherId(Long publisherId) {
        PublisherId = publisherId;
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
}
