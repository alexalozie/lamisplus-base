package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@Entity
@EqualsAndHashCode
@Table(name = "lab_test", schema = "public", catalog = "lamisplus")
public class LabTest {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "lab_test_group_id")
    private Long labTestGroupId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "unit_measurement")
    private String unitMeasurement;

    @Basic
    @Column(name = "uuid")
    @JsonIgnore
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
    @JsonIgnore
    private Integer archived;

    @ManyToOne
    @JoinColumn(name = "lab_test_group_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private LabTestGroup labTestGroupByLabTestGroupId;

}
