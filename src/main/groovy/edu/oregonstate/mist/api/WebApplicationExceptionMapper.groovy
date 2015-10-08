package edu.oregonstate.mist.api

import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.ext.Provider
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.WebApplicationException

@Provider
class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException exception) {
        ResponseBuilder responseBuilder
        if (exception.response == 404) {
            responseBuilder = Resource.notFound()
        }
        responseBuilder.build()
    }
}
