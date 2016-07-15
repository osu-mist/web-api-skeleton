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

    public InfoResource() {
        def stream = this.class.getResourceAsStream('/build.properties')
        if (stream == null) {
            throw new Exception("couldn't load build.properties")
        }

        def properties = new Properties()
        properties.load(stream)

        info.name = properties.get('name')
        info.time = Long.parseLong(properties.getProperty('time'))
        info.commit = properties.get('commit')
        info.documentation = properties.get('documentation')
    }

    /**
     * Responds to GET requests by returning object containing information.
     *
     * @return information
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@Auth AuthenticatedUser authenticatedUser) {
        ok(info).build()
    }
}
