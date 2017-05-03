package server.db;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import io.dropwizard.testing.junit.DAOTestRule;
import server.data.model.Movie;

import java.util.List;


/**
 * Test DAO
 */
public class MovieDaoTest {


    @Rule
    public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
            .addEntityClass(Movie.class)
            .build();

    private MovieDao movieDao;

    @Before
    public void setUp() throws Exception {
        movieDao = new MovieDao(daoTestRule.getSessionFactory());
    }

    @Test
    public void findAll() {
        daoTestRule.inTransaction(() -> {
            movieDao.create(new Movie("AAA", "G1", 2001, 4.0f));
            movieDao.create(new Movie("BBB", "G2", 2002, 4.1f));
            movieDao.create(new Movie("CCC", "G3", 2003, 4.2f));
        });

        final List<Movie> movies = movieDao.findAll();
        assertEquals(3, movies.size());
    }

    @Test
    public void createMovie() {
        final Movie movie = daoTestRule.inTransaction(() -> movieDao.create(new Movie("Forrest Gump", "Drama", 2000, 4.6f)));
        assertTrue(movie.getId() > 0);
        assertEquals("Forrest Gump", movie.getName());
        assertEquals("Drama", movie.getGenre());
        assertEquals(2000l, (long) movie.getYearReleased());
    }


}
