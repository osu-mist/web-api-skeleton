package edu.oregonstate.mist.api.jsonapi

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.Error

import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        Resource.notFound().build()
    }
}
