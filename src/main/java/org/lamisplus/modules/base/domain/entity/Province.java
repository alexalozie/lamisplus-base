package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "province", schema = "public", catalog = "lamisplus")
public class Province {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "state_id", insertable = false, updatable = false)
    @JsonIgnore
    private Long stateId;

    @Basic
    @Column(name = "uuid")
    private String uuid;

    @Basic
    @Column(name = "date_created")
    @JsonIgnore
    private Timestamp dateCreated;

    @Basic
    @Column(name = "created_by")
    @JsonIgnore
    private String createdBy;

    @Basic
    @Column(name = "date_modified")
    @JsonIgnore
    private Timestamp dateModified;

    @Basic
    @Column(name = "modified_by")
    @JsonIgnore
    private String modifiedBy;

    @Basic
    @Column(name = "archived")
    private Integer archived;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    @JsonIgnore
    private State stateByStateId;


    /*
    @OneToMany(mappedBy = "provinceByProvinceId")
    public Collection<PersonContact> getPersonContactsById() {
        return personContactsById;
    }

    public void setPersonContactsById(Collection<PersonContact> personContactsById) {
        this.personContactsById = personContactsById;
    }

     */
    /*@OneToMany(mappedBy = "provinceByProvinceId")
    private Collection<Organisation> organizationsById;*/
}
