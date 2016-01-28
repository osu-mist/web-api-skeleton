package edu.oregonstate.mist.webapiskeleton.core

import com.google.common.collect.Iterables
import io.katharsis.queryParams.QueryParams
import io.katharsis.repository.annotations.JsonApiDelete
import io.katharsis.repository.annotations.JsonApiFindAll
import io.katharsis.repository.annotations.JsonApiFindAllWithIds
import io.katharsis.repository.annotations.JsonApiFindOne
import io.katharsis.repository.annotations.JsonApiResourceRepository
import io.katharsis.repository.annotations.JsonApiSave
import io.katharsis.resource.exception.ResourceNotFoundException

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@JsonApiResourceRepository(Sample.class)
public class SampleRepository {
    // FIXME: this is a naive copy-paste from katharsis.io/start
    // TODO: i did not have a chance to go through the "Application" and "Testing" sections

    private static final Map<Long, Sample> REPOSITORY = new ConcurrentHashMap<>()
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1)

    @JsonApiSave
    public <S extends Sample> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(ID_GENERATOR.getAndIncrement())
        }
        REPOSITORY.put(entity.getId(), entity)
        return entity
    }

    @JsonApiFindOne
    public Sample findOne(Long id) { // Is the K at the end needed?
        Sample sample = REPOSITORY.get(id)
        if (sample == null) {
            throw new ResourceNotFoundException("Sample not found")
        }
        return Sample
    }

    @JsonApiFindAll
    public Iterable<Sample> findAll(QueryParams queryParams) {
        return REPOSITORY.values()
    }

    @JsonApiFindAllWithIds
    public Iterable<Sample> findAll(Iterable<Long> iterable, QueryParams queryParams) {
        return REPOSITORY.entrySet()
                .stream()
                .filter( { p -> Iterables.contains(iterable, p.getKey()) } )
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .collect(Collectors.toList())
                .values()
    }

    @JsonApiDelete
    public void delete(Long id) {
        REPOSITORY.remove(id)
    }
}
