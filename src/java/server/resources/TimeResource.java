package server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Return time
 */

@Path("/api")
public class TimeResource {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss Z");

    @GET
    @Path("/timeOfDay")
    public String getTimeOfDay() {
        // Java 8 has threadsafe formatter.
        String text = ZonedDateTime.now().format(FORMATTER);
        // JSON String issue: https://github.com/dropwizard/dropwizard/issues/231
        return String.format("\"%s\"", text);
    }

}
