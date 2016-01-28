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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@JsonApiResourceRepository(Sample.class)
public class SampleRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleRepository.class)

    private static final Map<Long, Sample> REPOSITORY = new ConcurrentHashMap<Long, Sample>()
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1)

    @JsonApiSave
    public <S extends Sample> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(ID_GENERATOR.getAndIncrement())
        }
        REPOSITORY.put(entity.getId(), entity)
        entity
    }

    @JsonApiFindOne
    public Sample findOne(Long id) { // Is the K at the end needed?
        Sample sample = REPOSITORY.get(id)
        if (sample == null) {
            throw new ResourceNotFoundException("Sample not found")
        }
        sample
    }

    @JsonApiFindAll
    public Iterable<Sample> findAll(QueryParams queryParams) {
        REPOSITORY.values()
    }

    @JsonApiFindAllWithIds
    public Iterable<Sample> findAll(Iterable<Long> iterable, QueryParams queryParams) {
        REPOSITORY.entrySet()
                .findAll { key, val -> Iterables.contains(iterable, key) }
                .values()
    }

    @JsonApiDelete
    public void delete(Long id) {
        REPOSITORY.remove(id)
    }
}
