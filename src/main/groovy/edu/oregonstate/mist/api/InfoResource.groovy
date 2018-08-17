package edu.oregonstate.mist.api

import io.dropwizard.auth.Auth
import javax.ws.rs.Path
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Information resource class.
 */
@Path('/')
class InfoResource extends Resource {
    private Info info = new Info()

    public InfoResource(Info info) {
        this.info = info
    }

    /**
     * Responds to GET requests by returning object containing information.
     *
     * @return information
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@Auth AuthenticatedUser authenticatedUser) {
        Response.ok(new InfoResultObject(meta: info)).build()
    }
}
