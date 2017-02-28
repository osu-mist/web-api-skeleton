package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * Error representation class.
 */
class Error {
    Integer status
    String developerMessage
    String userMessage
    Integer code
    String details

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
     * Returns a new Error for a HTTP 400 ("bad request") response.
     *
     * @param message the error message
     * @return error
     */
    static Error badRequest(String message) {
        new Error(
            status: 400,
            developerMessage: message,
            userMessage: prop.getProperty('badRequest.userMessage'),
            code: parseInt(prop.getProperty('badRequest.code')),
            details: prop.getProperty('badRequest.details')
        )
    }

    /**
     * Returns a new Error for a HTTP 404 ("not found") response.
     *
     * @return error
     */
    static Error notFound() {
        new Error(
            status: 404,
            developerMessage: prop.getProperty('notFound.developerMessage'),
            userMessage: prop.getProperty('notFound.userMessage'),
            code: parseInt(prop.getProperty('notFound.code')),
            details: prop.getProperty('notFound.details')
        )
    }

    /**
     * Returns a new Error for a HTTP 409 ("conflict") response.
     *
     * @return error
     */
    static Error conflict() {
        new Error(
            status: 409,
            developerMessage: prop.getProperty('conflict.developerMessage'),
            userMessage: prop.getProperty('conflict.userMessage'),
            code: parseInt(prop.getProperty('conflict.code')),
            details: prop.getProperty('conflict.details')
        )
    }

    /**
     * Returns a new Error for a HTTP 500 ("internal server error") response.
     *
     * @param message the error message
     * @return error
     */
    static Error internalServerError(String message) {
        new Error(
            status: 500,
            developerMessage: message,
            userMessage: prop.getProperty('internalServerError.userMessage'),
            code: parseInt(prop.getProperty('internalServerError.code')),
            details: prop.getProperty('internalServerError.details')
        )
    }

    private static Integer parseInt(String s) {
        if (s != null) {
            Integer.parseInt(s)
        }
    }
}
