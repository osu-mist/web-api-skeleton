package edu.oregonstate.mist.webapiskeleton.resources

import edu.oregonstate.mist.webapiskeleton.core.Sample
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.core.MediaType

/**
 * Sample resource class.
 */
@Path('/')
class SampleResource {
    /**
     * Responds to GET requests by returning a message.
     *
     * @return message
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        new Sample().message
    }

    /**
     * Responds to POST requests by reading and returning a message.
     *
     * @param message
     * @return message
     */
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String postMessage(String message) {
        message
    }
}
