package edu.oregonstate.mist.api

import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.ext.Provider
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.NotFoundException

@Provider
class NotFoundMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        ResponseBuilder responseBuilder
        responseBuilder = Resource.notFound()
        responseBuilder.build()
    }
}
