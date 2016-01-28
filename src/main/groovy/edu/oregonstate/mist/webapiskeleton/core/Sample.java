package edu.oregonstate.mist.webapiskeleton.core;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

/**
 * Sample representation class.
 */
@JsonApiResource(type = "sample")
public class Sample {
    private String message;

    @JsonApiId
    private Long id;

    private String name;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
