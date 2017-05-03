package server.data.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

/**
 * Movie data model
 */

@Entity
@Table(name = "movie")
@NamedQueries(
    {
        @NamedQuery(
                name = "Movie.findAll",
                query = "SELECT p FROM Movie p"
        )
    }
)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 3)
    private String name;
    private String genre;

    // TODO: Max should really be tested dynamically, say < current year + 10;
    @Range(min = 1900, max=2050)
    private Integer yearReleased;
    private Float rating;


    public Movie() {
    }

    public Movie(String name, String genre, int yearReleased, Float rating) {
        this();
        setName(name);
        setGenre(genre);
        setYearReleased(yearReleased);
        setRating(rating);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(Integer yearReleased) {
        this.yearReleased = yearReleased;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }


}
