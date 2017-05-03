package server.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.data.model.Movie;
import server.db.MovieDao;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Restful implementation of handling movies.
 *
 * Typically I would write the api as...
 * GET /movies
 * POST /movies {body}
 * PUT /movie/{uuid} {body}
 * DELETE /movie/{uuid}
 *
 * but the assignment called for this implementation
 */
@Path("/api/movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    final static Logger logger = LoggerFactory.getLogger(MovieResource.class);
    private MovieDao movieDao;

    public MovieResource(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/list")
    public Collection<Movie> getMovies() {
        List<Movie> all = movieDao.findAll();
        logger.info("Returning {} movies", all.size());
        return all;
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public Movie createMovie(@Valid Movie movie) {
        if (movie.getId() != null) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        movieDao.create(movie);
        logger.info("Created movie {}", movie.getId());
        return movie;
    }

    @PUT
    @UnitOfWork
    @Path("/update")
    public Movie updateMovie(@Valid Movie movie) {
        Optional<Movie> existing = movieDao.findById(movie.getId());
        if (!existing.isPresent()) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return movieDao.update(existing.get(), movie);
    }

    @DELETE
    @UnitOfWork
    @Path("/delete")
    public void deleteMovie(Movie movie) {
        movieDao.delete(movie);
    }


}