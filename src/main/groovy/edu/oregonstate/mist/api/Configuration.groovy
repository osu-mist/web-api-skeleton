package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * An object representation of the YAML configuration file.
 */
class Configuration extends io.dropwizard.Configuration {
    @JsonProperty('authentication')
    @NotNull
    @Valid
    List<Credentials> credentialsList

    @JsonProperty('api')
    @NotNull
    @Valid
    ApiConfiguration api
}
