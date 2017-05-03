package server.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import server.data.model.Movie;

import java.util.List;
import java.util.Optional;

/**
 * Movie access
 */
public class MovieDao extends AbstractDAO<Movie> {

    public MovieDao(SessionFactory factory) {
        super(factory);
    }


    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Movie create(Movie movie) {
        return persist(movie);
    }

    public Movie update(Movie found, Movie updates) {
        found.setName(updates.getName());
        found.setGenre(updates.getGenre());
        found.setYearReleased(updates.getYearReleased());
        found.setRating(updates.getRating());
        return persist(found);
    }

    public List<Movie> findAll() {
        return list(namedQuery("Movie.findAll"));
    }

    public void delete(Movie movie) {
        currentSession().delete(movie);
    }
}
