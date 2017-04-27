package server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Return time
 */

@Path("/api")
public class TimeResource {


    @GET
    @Path("/timeOfDay")
    public String getTimeOfDay() {
        // SimpleDateFormat is not thread-safe if used as instance variable, alternatively use joda time.
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        // JSON String issue: https://github.com/dropwizard/dropwizard/issues/231
        return String.format("\"%s\"", format.format(new Date()));
    }

}
