package edu.oregonstate.mist.api

import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.ext.Provider

@Provider
class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        ResponseBuilder responseBuilder
        if (exception.response == 404) {
            responseBuilder = Resource.notFound()
        }
        responseBuilder.build()
    }
}
