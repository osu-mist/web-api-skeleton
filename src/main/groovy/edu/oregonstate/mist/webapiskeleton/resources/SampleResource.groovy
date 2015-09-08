package edu.oregonstate.mist.webapiskeleton.resources

import edu.oregonstate.mist.webapiskeleton.core.Sample
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.core.MediaType

@Path('/')
class SampleResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        new Sample().message
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String postMessage(String message) {
        message
    }
}
