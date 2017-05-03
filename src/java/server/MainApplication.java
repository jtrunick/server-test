package server;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.data.model.Movie;
import server.db.MovieDao;
import server.healthcheck.AppHealthCheck;
import server.resources.MovieResource;
import server.resources.TimeResource;

/**
 * Main application
 */
public class MainApplication extends Application<AppConfiguration>
{
	final static Logger logger = LoggerFactory.getLogger(MainApplication.class);


	private final HibernateBundle<AppConfiguration> hibernateBundle =
			new HibernateBundle<AppConfiguration>(Movie.class) {
				@Override
				public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
					return configuration.getDataSourceFactory();
				}
			};

	public static void main(String[] args) throws Exception
	{
        new MainApplication().run(args);
    }

    @Override
    public String getName()
	{
        return "Test Server";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap)
	{
        // framework bootstrap initialization
		bootstrap.addBundle(hibernateBundle);
    }


    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception
	{
		try
		{
			logger.info("Starting...");
			DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
			Flyway flyway = new Flyway();
			flyway.setDataSource(dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
			flyway.migrate();

			// application initialization goes here
			final MovieDao dao = new MovieDao(hibernateBundle.getSessionFactory());
			environment.healthChecks().register("app", new AppHealthCheck());

			// register servlet route handlers
			final MovieResource movieResource = new MovieResource(dao);
			environment.jersey().register(movieResource);

			final TimeResource timeResource = new TimeResource();
			environment.jersey().register(timeResource);

		}
		catch (Exception exc)
		{
			// log failure to set up app
			logger.error("Failed to initialize application, exiting...", exc);
			throw new RuntimeException();
		}
    }
}
