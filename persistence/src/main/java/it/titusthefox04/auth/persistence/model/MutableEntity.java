package it.titusthefox04.auth.persistence.model;

import java.time.OffsetDateTime;

/**
 * All mutable entities that have a create-modify-delete lifecycle should implement this interface.
 *
 * @author titusthefox04
 */
public interface MutableEntity {

    OffsetDateTime getCreatedOn();

    OffsetDateTime getModifiedOn();

    OffsetDateTime getDeletedOn();

    boolean isDeleted();
}
