package server.data.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

/**
 * Movie data model
 */
public class Movie {

    private String uuid;
    @Length(min = 3)
    private String name;
    private String genre;
    @Range(min = 1900, max=2050)
    private int yearReleased;
    private Float rating;


    public Movie() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Movie(String name, String genre, int yearReleased, Float rating) {
        this();
        setName(name);
        setGenre(genre);
        setYearReleased(yearReleased);
        setRating(rating);
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    private void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    private void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public Float getRating() {
        return rating;
    }

    private void setRating(Float rating) {
        this.rating = rating;
    }


}
