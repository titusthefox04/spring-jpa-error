package it.titusthefox04.auth.persistence.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SortNatural;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

/**
 * User persistence model.
 *
 * @author titusthefox04
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users",
        indexes = @Index(name = "users_idx_username", columnList = "username", unique = true))
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    @Column(length = Models.S_LENGTH, nullable = false, unique = true)
    private String username;

    @Column
    private String salt;

    @Column
    private String hash;

    @Column(length = Models.CODE_LENGTH, nullable = false)
    @Enumerated(STRING)
    private AuthenticationType authType;

    @Column(length = Models.NAME_LENGTH, nullable = false)
    private String firstName;

    @Column(length = Models.NAME_LENGTH, nullable = false)
    private String lastName;

    @Column(length = Models.M_LENGTH)
    private String email;

    @Column(length = Models.CODE_LENGTH)
    private String phone;

    @Column(length = Models.CODE_LENGTH)
    private String timezone;
/*
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_secured_objects",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "user_secured_objects_fk", value = ConstraintMode.CONSTRAINT)),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "id"}))
    private List<SecuredObject> securedObjects;
*/
    @Column
    private boolean enabled;

    @Column
    private Integer creator;

    @Column
    private Integer modifier;

    @Column
    private boolean firstLogin;

    @Column(length = Models.M_LENGTH)
    private String totpSecret;

    @Column
    @ColumnDefault("true")
    private boolean totpRequired;

    @Column
    @ColumnDefault("true")
    private boolean passwordExpires;

    @Column
    @ColumnDefault("true")
    private boolean human;

    @Column
    private OffsetDateTime passwordChangedOn;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "user_password_history",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "user_pwd_hist_fk", value = ConstraintMode.CONSTRAINT)),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "created_on", "hash", "salt"}))
    @OrderBy("created_on asc")
    @SortNatural
    private List<PasswordHistory> passwordHistory = new LinkedList<>();

    public User() {
        //fixme replace PersistentBag with an ArrayList
        //   passwordHistory = new LinkedList<>();
        //   securedObjects = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public AuthenticationType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthenticationType authType) {
        this.authType = authType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
/*
    public List<SecuredObject> getSecuredObjects() {
        return securedObjects;
    }

    public void setSecuredObjects(List<SecuredObject> securedObjects) {
        this.securedObjects = securedObjects;
    }*/

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getTotpSecret() {
        return totpSecret;
    }

    public void setTotpSecret(String totpSecret) {
        this.totpSecret = totpSecret;
    }

    public boolean getPasswordExpires() {
        return passwordExpires;
    }

    public void setPasswordExpires(boolean passwordExpires) {
        this.passwordExpires = passwordExpires;
    }

    public OffsetDateTime getPasswordChangedOn() {
        return passwordChangedOn;
    }

    public void setPasswordChangedOn(OffsetDateTime passwordChangedOn) {
        this.passwordChangedOn = passwordChangedOn;
    }

    public List<PasswordHistory> getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(List<PasswordHistory> passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public boolean isTotpRequired() {
        return totpRequired;
    }

    public void setTotpRequired(boolean totpRequired) {
        this.totpRequired = totpRequired;
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }


}
