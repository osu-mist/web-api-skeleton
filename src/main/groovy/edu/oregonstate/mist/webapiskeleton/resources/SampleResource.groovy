package edu.oregonstate.mist.webapiskeleton.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.webapiskeleton.core.Sample
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.core.MediaType

/**
 * Sample resource class.
 */
@Path('/')
class SampleResource extends Resource {
    /**
     * Responds to GET requests by returning a message.
     *
     * @return message
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMessage() {
        ResponseBuilder responseBuilder = ok(new Sample().message)
        responseBuilder.build()
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
    public Response postMessage(String message) {
        ResponseBuilder responseBuilder = ok(message)
        responseBuilder.build()
    }
}
