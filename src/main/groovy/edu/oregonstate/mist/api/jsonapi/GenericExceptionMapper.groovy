package edu.oregonstate.mist.api.jsonapi

import edu.oregonstate.mist.api.Error
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

/**
 * Created by georgecrary on 8/5/16.
 */
@Provider
class GenericExceptionMapper implements ExceptionMapper<Exception> {
    Logger logger = LoggerFactory.getLogger(GenericExceptionMapper.class)

    @Override
    public Response toResponse(Exception e) {
        logger.error("Generic exception occured and caught by GEMapper. Please map $e" ,e)

        Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        builder.entity(new Error(
                status: Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                developerMessage: e.message,
                userMessage: "Generic Exception. Please contact a dev.",
                code: Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                details: "Unmapped specific exception"
        )).build()
    }
}