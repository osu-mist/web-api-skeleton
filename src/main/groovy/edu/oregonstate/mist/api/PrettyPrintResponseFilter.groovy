package edu.oregonstate.mist.api

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterInjector
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterModifier
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.core.MultivaluedMap

@groovy.transform.TypeChecked
public class PrettyPrintResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {

        MultivaluedMap<String, String> queryParams =
            requestContext.getUriInfo().getQueryParameters()

        if (queryParams.getFirst('pretty')) {
            if (queryParams.getFirst('pretty').equalsIgnoreCase('true')) {
                ObjectWriterInjector.set(new ObjectWriterModifier() {
                    @Override
                    public ObjectWriter modify(
                        EndpointConfigBase<?> endpoint,
                        MultivaluedMap<String, Object> responseHeaders,
                        Object valueToWrite,
                        ObjectWriter w,
                        JsonGenerator g
                    ) {
                        g.useDefaultPrettyPrinter()
                        w
                    }
                })
            }
        }
    }

}
