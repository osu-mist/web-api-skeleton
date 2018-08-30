package edu.oregonstate.mist.api

/**
 * Object containing build and runtime information about the application.
 */
class Info {
    String name
    String time
    Long unixTime
    String commit
    String documentation
}

/**
 * Wrapper object for returning info in a response
 */
class InfoResultObject {
    Info meta
}
