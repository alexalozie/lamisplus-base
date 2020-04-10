package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
public class Organisation {
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "org_group")
    private String orgGroup;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "phone1")
    private String phone1;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "pin")
    private String pin;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "state_id")
    private String stateId;

    @Basic
    @Column(name = "province_id")
    private String provinceId;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private State stateByStateId;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Province provinceByProvinceId;
}
