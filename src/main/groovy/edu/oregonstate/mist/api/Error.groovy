package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * Error representation class.
 */
class Error {
    String status
    String[] links
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
     * Returns a new Error for a HTTP 400 ("bad request") response.
     *
     * @param message the error message
     * @return error
     */
    static ErrorResultObject badRequest(String message) {
        new ErrorResultObject(
            errors: [new Error(
                         status: "400",
                         links: prop.getProperty('badRequest.links')
                            .replaceAll('\\s', '').split(','),
                         code: prop.getProperty('badRequest.code'),
                         title: prop.getProperty('badRequest.title'),
                         detail: message
                         )
                    ]
            )
    }

    /**
     * Returns a new Error for a HTTP 404 ("not found") response.
     *
     * @return error
     */
    static ErrorResultObject notFound(String message) {
        new ErrorResultObject(
            errors: [new Error(
                         status: "404",
                         links: prop.getProperty('notFound.links').split(',')
                            .replaceAll('\\s', '').split(','),
                         code: prop.getProperty('notFound.code'),
                         title: prop.getProperty('notFound.title'),
                         detail: message
                         )
                    ]
            )
    }

    /**
     * Returns a new Error for a HTTP 409 ("conflict") response.
     *
     * @return error
     */
    static ErrorResultObject conflict(String message) {
        new ErrorResultObject(
            errors: [new Error(
                         status: "409",
                         links: prop.getProperty('conflict.links').split(',')
                            .replaceAll('\\s', '').split(','),
                         code: prop.getProperty('conflict.code'),
                         title: prop.getProperty('conflict.title'),
                         detail: message
                         )
                    ]
            )
    }

    /**
     * Returns a new Error for a HTTP 500 ("internal server error") response.
     *
     * @param message the error message
     * @return error
     */
    static ErrorResultObject internalServerError(String message) {
        new ErrorResultObject(
            errors: [new Error(
                         status: "500",
                         links: prop.getProperty('internalServerError.links')
                            .replaceAll('\\s', '').split(','),
                         code: prop.getProperty('internalServerError.code'),
                         title: prop.getProperty('internalServerError.title'),
                         detail: message
                         )
                    ]
            )
    }

    private static Integer parseInt(String s) {
        if (s != null) {
            Integer.parseInt(s)
        }
    }
}

/**
 * Wrapper object for returning error(s) in a response
 */
 class ErrorResultObject {
     Error[] errors
 }
