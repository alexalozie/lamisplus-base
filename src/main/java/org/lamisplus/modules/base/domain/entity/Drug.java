package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "brand_name")
    private String brandName;

    @Basic
    @Column(name = "generic_name")
    private String genericName;

    @Basic
    @Column(name = "strength")
    private String strength;

    @Basic
    @Column(name = "pack_size")
    private Integer packSize;

    @Basic
    @Column(name = "dosage_form")
    private String dosageForm;

    @Basic
    @Column(name = "abbrev")
    private String name;

    @Basic
    @Column(name = "drug_group_id")
    public Long drugGroupId;

    @ManyToOne
    @JoinColumn(name = "drug_group_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    public DrugGroup drugGroupByDrugGroupId;

}