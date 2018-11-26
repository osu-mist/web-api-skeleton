package edu.oregonstate.mist.api

import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import org.apache.http.client.utils.URIBuilder

import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo

/**
 * Abstract class for reusing common response messages.
 */
abstract class Resource {
    /**
     * Default page number used in pagination
     */
    public static final Integer DEFAULT_PAGE_NUMBER = 1

    /**
     * Default page size used in pagination
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10

    /**
     * Default max page size used in pagination.
     */
    public static Integer MAX_PAGE_SIZE = 10000

    @Context
    UriInfo uriInfo

    /**
     * URI used to provide jsonapi pagination links.
     */
    private URI endpointUri

    /**
     * Set the base URI used to provide JSON-API pagination links.
     *
     * @param endpointUri the base URI
     */
    void setEndpointUri(URI endpointUri) {
        this.endpointUri = endpointUri
    }

    /**
     * Returns a builder for an HTTP 200 ("ok") response with the argument entity as body.
     *
     * @param entity
     * @return ok response builder
     */
    protected static ResponseBuilder ok(Object entity) {
        Response.ok().entity(entity)
    }

    /**
     * Returns a builder for an HTTP 201 ("created") response with the argument entity as body.
     *
     * @param entity
     * @return created response builder
     */
    protected static ResponseBuilder created(Object entity) {
        Response.status(Response.Status.CREATED)
                .entity(entity)
    }

    /**
     * Returns a builder for an HTTP 202 ("accepted") response with the argument entity as body.
     * @param entity
     * @return
     */
    protected static ResponseBuilder accepted(Object entity) {
        Response.status(Response.Status.ACCEPTED)
                .entity(entity)
    }

    /**
     * Returns a builder for an HTTP 204 ("no content") response.
     * @param entity
     * @return
     */
    protected static ResponseBuilder noContent() {
        Response.status(Response.Status.NO_CONTENT)
    }

    /**
     * Returns a builder for an HTTP 400 ("bad request") response with message argument as detail
     *
     * @param message
     * @return bad request response builder
     */
    protected static ResponseBuilder badRequest(String message='') {
        Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResultObject(
                    errors:[Error.badRequest(message)])
                )
    }

    /**
     * Returns a builder for an HTTP 400 when page[size] exceeds MAX_PAGE_SIZE
     *
     * @return bad request response builder
     */
    protected static ResponseBuilder pageSizeExceededError() {
        Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResultObject(
                    errors:[Error.badRequest("page[size] cannot exceed ${MAX_PAGE_SIZE}.")])
                )
    }

    /**
     * Returns a builder for an HTTP 403 ("forbidden") response with message argument as detail
     *
     * @return forbidden response builder
     */
    protected static ResponseBuilder forbidden(String message='') {
        Response.status(Response.Status.FORBIDDEN)
                .entity(new ErrorResultObject(
                    errors:[Error.forbidden(message)])
                )
    }

    /**
     * Returns a builder for an HTTP 404 ("not found") response with message argument as detail
     *
     * @return not found response builder
     */
    protected static ResponseBuilder notFound(String message='') {
        Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResultObject(
                    errors:[Error.notFound(message)])
                )
    }

    /**
     * Returns a builder for an HTTP 409 ("conflict") response with message argument as detail
     *
     * @return conflict response builder
     */
    protected static ResponseBuilder conflict(String message='') {
        Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResultObject(
                    errors:[Error.conflict(message)])
                )
    }

    /**
     * Returns a builder for an HTTP 500 ("internal server error") response
     *    with message argument as detail
     *
     * @return internal server error response builder
     */
    protected static ResponseBuilder internalServerError(String message='') {
        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResultObject(
                    errors:[Error.internalServerError(message)])
                )
    }

    /**
     * Returns a builder for an error response with multiple errors in the errors object
     *
     * Usage: compoundError([Error.notFound(), Error.forbidden()], 400).build()
     *
     * @param errors is a list of Error objects
     * @param status is the HTTP response code for the compound eror response. The developer
     *      is responsible for supplying "the most generally applicable HTTP error code"
     * @return compound error response builder
     */
    protected static ResponseBuilder compoundError(List<Error> errors, int status) {
            Response.status(status)
                    .entity(new ErrorResultObject(errors: errors))
    }

    /**
     * Constructs a url to use in pagination links.
     *
     * The path is copied from the request path.
     * The query parameters are taken from the params argument.
     *
     * Falsey params are omitted from the url.
     * The parameters pageNumber and pageSize are
     * converted to page[number] and page[size].
     *
     * @param params a map of query parameters for the url
     * @param resourceEndpoint: the endpoint to be appended on the uri.
     * @return the url
     */
    protected String getPaginationUrl(Map params, String resourceEndpoint) {
        URI baseUri = UriBuilder.fromUri(endpointUri).path(resourceEndpoint).build()
        URIBuilder uriBuilder = new URIBuilder(endpointUri).setPath(baseUri.path)
        // use a copy of params since other parameters could be present
        def nonNullParams = params.clone()
        nonNullParams.remove('pageSize')
        nonNullParams.remove('pageNumber')

        // convert pageVariable to page[variable]
        nonNullParams["page[number]"] = params['pageNumber']
        nonNullParams["page[size]"] = params['pageSize']

        nonNullParams.findAll { it.value } .collect { k, v ->
            uriBuilder.setParameter(k, v.toString())
        }

        uriBuilder.build().toString()
    }

    /**
     *  Returns the page number used by pagination. The value of: page[number] in the url.
     *
     * @return
     */
    protected Integer getPageNumber() {
        def pageNumber = uriInfo.getQueryParameters().getFirst('page[number]')
        if (!pageNumber || !pageNumber.isInteger() || pageNumber.toInteger() <= 0) {
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
        if (!pageSize || !pageSize.isInteger() || pageSize.toInteger() <= 0) {
            return DEFAULT_PAGE_SIZE
        }

        pageSize.toInteger()
    }

    /**
     * Returns true if page[size] exceeds MAX_PAGE_SIZE
     *
     * @return
     */
    protected Boolean maxPageSizeExceeded() {
        getPageSize() > MAX_PAGE_SIZE
    }
}
