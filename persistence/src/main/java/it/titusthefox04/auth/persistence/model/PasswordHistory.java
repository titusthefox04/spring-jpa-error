package it.titusthefox04.auth.persistence.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.OffsetDateTime;

/**
 * User persistence model.
 *
 * @author titusthefox04
 */
@Embeddable
public class PasswordHistory implements Comparable<PasswordHistory>{
    public PasswordHistory() {}

    public PasswordHistory(String salt, String hash) {
        this.salt = salt;
        this.hash = hash;
        this.createdOn = OffsetDateTime.now();
    }

    @Column(nullable = false, name = "created_on")
    @CreatedDate
    private OffsetDateTime createdOn;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String hash;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public int compareTo(PasswordHistory o) {
        return this.createdOn.compareTo(o.createdOn);
    }
}
