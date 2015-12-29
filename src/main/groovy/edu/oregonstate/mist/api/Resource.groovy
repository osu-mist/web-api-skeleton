package edu.oregonstate.mist.api

import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder

/**
 * Abstract class for reusing common response messages.
 */
abstract class Resource {
    protected static Properties properties = new Properties()

    public static loadProperties(String fileName) {
        properties.load(new FileReader(fileName))
    }

    /**
     * Returns a builder for an HTTP 200 ("ok") response with the argument entity as body.
     *
     * @param entity
     * @return ok response builder
     */
    protected static ResponseBuilder ok(Object entity) {
        ResponseBuilder responseBuilder = Response.ok()
        responseBuilder.entity(entity)
    }

    /**
     * Returns a builder for an HTTP 201 ("created") response with the argument entity as body.
     *
     * @param entity
     * @return created response builder
     */
    protected static ResponseBuilder created(Object entity) {
        ResponseBuilder responseBuilder = Response.status(Response.Status.CREATED)
        responseBuilder.entity(entity)
    }

    /**
     * Returns a builder for an HTTP 400 ("bad request") response with an error message as body.
     *
     * @param message
     * @return bad request response builder
     */
    protected static ResponseBuilder badRequest(String message) {
        ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST)
        responseBuilder.entity(new Error(
                status: 400,
                developerMessage: message,
                userMessage: properties.get('badRequest.userMessage'),
                code: Integer.parseInt(properties.get('badRequest.code')),
                details: properties.get('badRequest.details')
        ))
    }

    /**
     * Returns a builder for an HTTP 404 ("not found") response with an error message as body.
     *
     * @return not found response builder
     */
    protected static ResponseBuilder notFound() {
        ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND)
        responseBuilder.entity(new Error(
                status: 404,
                developerMessage: properties.get('notFound.developerMessage'),
                userMessage: properties.get('notFound.userMessage'),
                code: Integer.parseInt(properties.get('notFound.code')),
                details: properties.get('notFound.details')
        ))
    }

    /**
     * Returns a builder for an HTTP 500 ("internal server error") response with an error message as body.
     *
     * @return internal server error response builder
     */
    protected static ResponseBuilder internalServerError(String message) {
        ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        responseBuilder.entity(new Error(
                status: 500,
                developerMessage: message,
                userMessage: properties.get('internalServerError.userMessage'),
                code: Integer.parseInt(properties.get('internalServerError.code')),
                details: properties.get('internalServerError.details')
        ))
    }
}
