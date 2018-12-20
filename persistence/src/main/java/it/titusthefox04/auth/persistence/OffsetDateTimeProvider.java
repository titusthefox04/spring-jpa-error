package it.titusthefox04.auth.persistence;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * Provider that creates a {@link TemporalAccessor temporal accessor} for {@link OffsetDateTime}.
 *
 * @author titusthefox04
 */
public enum OffsetDateTimeProvider implements DateTimeProvider {

    INSTANCE;

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(OffsetDateTime.now());
    }
}
