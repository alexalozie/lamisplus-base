package org.lamisplus.base.domain.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.Objects;

@Entity
public class User {
    private Long id;
    private String userName;
    private String password;
    private Integer active;
    private Date dateCreated;
    private String lastModifiedBy;
    private Date dateLastModified;
    private String activationKey;
    private Date dateReset;
    private String resetKey;
    private Integer uploaded;
    private Time timeUploaded;
    private Long personId;
    private Person personByPersonId;
    private Collection<UserHasPriviledge> userHasPriviledgesById;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = -1)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "active", nullable = true)
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Basic
    @Column(name = "date_created", nullable = true)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "last_modified_by", nullable = true, length = -1)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "date_last_modified", nullable = true)
    public Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(Date dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    @Basic
    @Column(name = "activation_key", nullable = true, length = -1)
    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    @Basic
    @Column(name = "date_reset", nullable = true)
    public Date getDateReset() {
        return dateReset;
    }

    public void setDateReset(Date dateReset) {
        this.dateReset = dateReset;
    }

    @Basic
    @Column(name = "reset_key", nullable = true, length = -1)
    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    @Basic
    @Column(name = "uploaded", nullable = true)
    public Integer getUploaded() {
        return uploaded;
    }

    public void setUploaded(Integer uploaded) {
        this.uploaded = uploaded;
    }

    @Basic
    @Column(name = "time_uploaded", nullable = true)
    public Time getTimeUploaded() {
        return timeUploaded;
    }

    public void setTimeUploaded(Time timeUploaded) {
        this.timeUploaded = timeUploaded;
    }

    @Basic
    @Column(name = "person_id", nullable = false, insertable = false, updatable = false)
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(active, user.active) &&
                Objects.equals(dateCreated, user.dateCreated) &&
                Objects.equals(lastModifiedBy, user.lastModifiedBy) &&
                Objects.equals(dateLastModified, user.dateLastModified) &&
                Objects.equals(activationKey, user.activationKey) &&
                Objects.equals(dateReset, user.dateReset) &&
                Objects.equals(resetKey, user.resetKey) &&
                Objects.equals(uploaded, user.uploaded) &&
                Objects.equals(timeUploaded, user.timeUploaded) &&
                Objects.equals(personId, user.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, active, dateCreated, lastModifiedBy, dateLastModified, activationKey, dateReset, resetKey, uploaded, timeUploaded, personId);
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserHasPriviledge> getUserHasPriviledgesById() {
        return userHasPriviledgesById;
    }

    public void setUserHasPriviledgesById(Collection<UserHasPriviledge> userHasPriviledgesById) {
        this.userHasPriviledgesById = userHasPriviledgesById;
    }
}
