package it.titusthefox04.auth.persistence.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Any mutable entity that is identifiable.
 *
 * @author titusthefox04
 */
@MappedSuperclass
public abstract class BaseEntity implements MutableEntity, IdentifiableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @CreatedDate
    private OffsetDateTime createdOn;

    @LastModifiedDate
    private OffsetDateTime modifiedOn;

    private OffsetDateTime deletedOn;

    private boolean deleted;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public OffsetDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(OffsetDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public OffsetDateTime getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(OffsetDateTime deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
