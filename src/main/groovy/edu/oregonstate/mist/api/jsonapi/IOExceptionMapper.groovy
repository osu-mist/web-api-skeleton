package edu.oregonstate.mist.api.jsonapi

import edu.oregonstate.mist.api.Error
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

/**
 * Created by georgecrary on 8/4/16.
 */
@Provider
class IOExceptionMapper implements ExceptionMapper<IOException> {
    Logger logger = LoggerFactory.getLogger(IOExceptionMapper.class)

    @Override
    public Response toResponse(IOException e) {
        logger.error("IOException occured.",e)

        Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        builder.entity(new Error(
                status: Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                developerMessage: e.message,
                userMessage: "Internal Server Error occured. IOException. Please contact a dev.",
                code: Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() + 1000,
                details: "IOException mapped and caught"
        )).build()
    }
}