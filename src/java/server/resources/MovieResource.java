package server.resources;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.data.model.Movie;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private Map<String, Movie> moviesByUuid;

    public MovieResource() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "Action,Sci-fi", 1999, 4.3f));
        movies.add(new Movie("From Russia with Love", "Action", 1963, 4.2f));
        movies.add(new Movie("Star Wars", "Action,Sci-fi", 2017, null));

        moviesByUuid = movies.stream().collect(Collectors.toMap(x -> x.getUuid(), x -> x));
    }

    @GET
    @Timed
    @Path("/list")
    public Collection<Movie> getMovies() {
        logger.info("Returning {} movies", moviesByUuid.size());
        return moviesByUuid.values();
    }

    @POST
    @Path("/create")
    public Movie createMovie(@Valid Movie movie) {
        if (moviesByUuid.containsKey(movie.getUuid())) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        moviesByUuid.put(movie.getUuid(), movie);
        logger.info("Created movie {}", movie.getUuid());
        return movie;
    }

    @PUT
    @Path("/update")
    public Movie updateMovie(@Valid Movie movie) {
        Movie existing = moviesByUuid.get(movie.getUuid());
        if (existing == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        moviesByUuid.put(movie.getUuid(), movie);
        return movie;
    }

    @DELETE
    @Path("/delete")
    public void deleteMovie(Movie movie) {
        moviesByUuid.remove(movie.getUuid());
    }


}