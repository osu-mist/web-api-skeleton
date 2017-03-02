package edu.oregonstate.mist.api.jsonapi

import edu.oregonstate.mist.api.Resource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class GenericExceptionMapper implements ExceptionMapper<Exception> {
    Logger logger = LoggerFactory.getLogger(GenericExceptionMapper.class)

    @Override
    public Response toResponse(Exception e) {
        logger.error("Uncaught exception", e)
        Resource.internalServerError("Uncaught exception: ${e.class.name}").build()
    }
}
