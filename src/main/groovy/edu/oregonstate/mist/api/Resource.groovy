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
     * Returns a builder for an HTTP 400 ("bad request") response with an error message as body.
     *
     * @param message
     * @return bad request response builder
     */
    protected static ResponseBuilder badRequest(String message) {
        Response.status(Response.Status.BAD_REQUEST)
                .entity(Error.badRequest(message))
    }

    /**
     * Returns a builder for an HTTP 404 ("not found") response with an error message as body.
     *
     * @return not found response builder
     */
    protected static ResponseBuilder notFound() {
        Response.status(Response.Status.NOT_FOUND)
                .entity(Error.notFound())
    }

    /**
     * Returns a builder for an HTTP 409 ("conflict") response with an error message as body.
     *
     * @return conflict response builder
     */
    protected static ResponseBuilder conflict() {
        Response.status(Response.Status.CONFLICT)
                .entity(Error.conflict())
    }

    /**
     * Returns a builder for an HTTP 500 ("internal server error") response with an error message
     * as body.
     *
     * @return internal server error response builder
     */
    protected static ResponseBuilder internalServerError(String message) {
        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Error.internalServerError(message))
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
        if (!pageNumber || !pageNumber.isInteger() || pageNumber.toInteger() < 0) {
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
        if (!pageSize || !pageSize.isInteger() || pageSize.toInteger() < 0) {
            return DEFAULT_PAGE_SIZE
        }

        pageSize.toInteger()
    }
}
