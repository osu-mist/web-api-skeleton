package edu.oregonstate.mist.api.jsonapi

class ResultObject {
    /**
     * Holds links to the current search and pagination links to allow the
     * user to paginate through the results. The keys that can be expected
     * in this map are: self, first, last, prev, and next.
     *
     * @optional
     */
    def links = [:]

    /**
     * Holds information about either a single object or a list of objects.
     * When the request is for a list of objects, data will hold a list of
     * ResourceObjects or an empty list.
     * When the request is for a single object, data will hold an instance of
     * ResourceObject.
     *
     * @required
     */
    def data
}
