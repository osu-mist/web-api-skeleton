package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotNull

class Configuration extends io.dropwizard.Configuration {
    @JsonProperty('authentication')
    @NotNull
    @Valid
    List<Credentials> credentialsList
}
