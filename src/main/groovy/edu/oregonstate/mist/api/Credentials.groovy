package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonProperty

class Credentials {
    @JsonProperty
    String username

    @JsonProperty
    String password
}
