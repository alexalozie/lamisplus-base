package org.lamisplus.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Entity
@Table(name = "facility_service", schema = "public", catalog = "lamisplus")
@EqualsAndHashCode
public class FacilityService {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "day_service")
    private String dayService;

    @Basic
    @Column(name = "time_service")
    private String timeService;

    @Basic
    @Column(name = "charge", nullable = true, length = -1)
    private String charge;

    @Basic
    @Column(name = "facility_id", nullable = false, insertable = false, updatable = false)
    private Long facilityId;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", nullable = false)
    private Facility facilityByFacilityId;

}
