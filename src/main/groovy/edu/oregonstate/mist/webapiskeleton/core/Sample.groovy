package edu.oregonstate.mist.webapiskeleton.core

import io.katharsis.resource.annotations.JsonApiId
import io.katharsis.resource.annotations.JsonApiResource

/**
 * Sample representation class.
 */
@JsonApiResource(type = 'sample')
class Sample {
    String message = 'hello world'

    @JsonApiId
    Long id

    String name
}
