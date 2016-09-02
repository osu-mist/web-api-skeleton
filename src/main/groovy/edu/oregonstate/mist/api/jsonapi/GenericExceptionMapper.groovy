package edu.oregonstate.mist.api.jsonapi

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.Error
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
        logger.error("Generic exception occured and caught by GenericExceptionMapper." +
                "Please map the exception. $e" ,e)

        Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        builder.entity(new Error(
                status: Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                developerMessage: "Generic exception caught and mapped.",
                userMessage: "Generic Exception. Please contact a dev.",
                code: Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() + 1000,
                details: "Unmapped specific exception"
        )).build()
    }
}