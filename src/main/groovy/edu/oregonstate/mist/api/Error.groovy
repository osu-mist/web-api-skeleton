package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * Error representation class.
 */
class Error {
    String status
    LinkObject links
    String code
    String title
    String detail

    @JsonIgnore
    private static Properties prop = new Properties()

    /**
     * Static initializer to load error text
     * from the resource.properties file.
     */
    static {
        def stream = this.getResourceAsStream('resource.properties')
        if (stream == null) {
            throw new IOException("couldn't open resource.properties")
        }
        prop.load(stream)
        stream.close()
    }

    /**
     * Returns a new Error object for a HTTP 400 ("bad request") response.
     *
     * @param message the detailed error message, may be occurence-specific
     * @return Error
     */
    static Error badRequest(String message) {
        new Error(
                status: "400",
                links: new LinkObject(about: prop.getProperty('badRequest.links')),
                code: prop.getProperty('badRequest.code'),
                title: prop.getProperty('badRequest.title'),
                detail: message ?: prop.getProperty('badRequest.detail')
            )
    }

    /**
     * Returns a new Error object for a HTTP 403 ("forbidden") response.
     *
     * @param message the detailed error message, may be occurence-specific
     * @return Error
     */
    static Error forbidden(String message) {
        new Error(
                status: "403",
                links: new LinkObject(about: prop.getProperty('forbidden.links')),
                code: prop.getProperty('forbidden.code'),
                title: prop.getProperty('forbidden.title'),
                detail: message ?: prop.getProperty('forbidden.detail')
            )
    }

    /**
     * Returns a new Error object for a HTTP 404 ("not found") response.
     *
     * @param message the detailed error message, may be occurence-specific
     * @return Error
     */
    static Error notFound(String message) {
        new Error(
                status: "404",
                links: new LinkObject(about: prop.getProperty('notFound.links')),
                code: prop.getProperty('notFound.code'),
                title: prop.getProperty('notFound.title'),
                detail: message ?: prop.getProperty('notFound.detail')
            )
    }

    /**
     * Returns a new Error object for a HTTP 409 ("conflict") response.
     *
     * @param message the detailed error message, may be occurence-specific
     * @return Error
     */
    static Error conflict(String message) {
        new Error(
                status: "409",
                links: new LinkObject(about: prop.getProperty('conflict.links')),
                code: prop.getProperty('conflict.code'),
                title: prop.getProperty('conflict.title'),
                detail: message ?: prop.getProperty('conflict.detail')
            )
    }

    /**
     * Returns a new Error object for a Error for a HTTP 500
     * ("internal server error") response.
     *
     * @param message the detailed error message, may be occurence-specific
     * @return Error
     */
    static Error internalServerError(String message) {
        new Error(
                 status: "500",
                 links: new LinkObject(
                 about: prop.getProperty('internalServerError.links')),
                 code: prop.getProperty('internalServerError.code'),
                 title: prop.getProperty('internalServerError.title'),
                 detail: message ?: prop.getProperty('internalServerError.detail')
            )
    }

    private static Integer parseInt(String s) {
        if (s != null) {
            Integer.parseInt(s)
        }
    }
}

/**
 * Object that contains an "about" link member for detailed error information
 */
class LinkObject {
    String about
}

/**
 * Wrapper object for returning error(s) in a response
 */
 class ErrorResultObject {
     Error[] errors
 }
