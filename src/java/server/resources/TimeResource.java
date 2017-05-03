package server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Return time
 */

@Path("/api")
public class TimeResource {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ssa z");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timeOfDay")
    public String getTimeOfDay() {
        // Java 8 has threadsafe formatter.
        String text = ZonedDateTime.now().format(FORMATTER);
        // JSON String issue: https://github.com/dropwizard/dropwizard/issues/231
        return String.format("\"%s\"", text);
    }

}
