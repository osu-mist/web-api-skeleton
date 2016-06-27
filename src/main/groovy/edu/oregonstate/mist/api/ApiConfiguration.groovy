package edu.oregonstate.mist.api

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * Configuration parameters common to all APIs
 */
class ApiConfiguration {
    @JsonProperty('endpointUri')
    @NotNull
    @Valid
    URI endpointUri
}

