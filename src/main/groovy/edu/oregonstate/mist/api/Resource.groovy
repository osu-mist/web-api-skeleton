package edu.oregonstate.mist.api

import javax.ws.rs.core.Context
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.core.UriInfo

/**
 * Abstract class for reusing common response messages.
 */
abstract class Resource {
    protected static Properties properties = new Properties()

    /**
     * Default page number used in pagination
     */
    public static final Integer DEFAULT_PAGE_NUMBER = 1

    /**
     * Default page size used in pagination
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10

    @Context
    UriInfo uriInfo

    /**
     * URI used to provide jsonapi pagination links.
     */
    private URI endpointUri

    public static loadProperties() {
        def stream = this.getResourceAsStream('resource.properties')
        if (stream == null) {
            throw new Exception("couldn't open resource.properties")
        }
        properties.load(stream)
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
     * Returns a builder for an HTTP 500 ("internal server error") response with an error message
     * as body.
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

    void setEndpointUri(URI endpointUri) {
        this.endpointUri = endpointUri
    }

    /**
     * Returns string url to use in pagination links.
     *
     * @param params
     * @return
     */
    protected String getPaginationUrl(def params) {
        def uriAndPath = endpointUri.toString() + uriInfo.getPath()
        def nonNullParams = params.clone()
        // convert pageVariable to page[variable]
        nonNullParams["page[number]"] = nonNullParams['pageNumber']
        nonNullParams["page[size]"] = nonNullParams['pageSize']
        nonNullParams.remove('pageSize')
        nonNullParams.remove('pageNumber')

        // remove empty GET parameters
        nonNullParams = nonNullParams.findAll { it.value } .collect { k, v -> "$k=$v" }

        uriAndPath + "?" + nonNullParams.join('&')
    }

    /**
     *  Returns the page number used by pagination. The value of: page[number] in the url.
     *
     * @return
     */
    protected Integer getPageNumber() {
        def pageNumber = uriInfo.getQueryParameters().getFirst('page[number]')
        if (!pageNumber || !pageNumber.isInteger()) {
            return DEFAULT_PAGE_NUMBER
        }

        pageNumber.toInteger()
    }

    /**
     * Returns the page size used by pagination. The value of: page[size] in the url.
     *
     * @return
     */
    protected Integer getPageSize() {
        def pageSize = uriInfo.getQueryParameters().getFirst('page[size]')
        if (!pageSize || !pageSize.isInteger()) {
            return DEFAULT_PAGE_SIZE
        }

        pageSize.toInteger()
    }
}
