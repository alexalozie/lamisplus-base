package org.lamisplus.modules.base.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


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
    @Column(name = "lab_test_category_id", nullable = true)
    private Long labTestCategoryId;

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;

    @Basic
    @Column(name = "unit_measurement", nullable = true, length = -1)
    private String unitMeasurement;

    @ManyToOne
    @JoinColumn(name = "lab_test_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private LabTestGroup labTestGroupByLabTestCategoryId;

}
