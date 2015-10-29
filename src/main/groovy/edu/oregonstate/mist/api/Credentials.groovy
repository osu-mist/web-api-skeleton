package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * An object representing credentials parsed from the YAML configuration file.
 */
class Credentials {
    @JsonProperty
    String username

    @JsonProperty
    String password
}
