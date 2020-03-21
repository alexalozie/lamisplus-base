package org.lamisplus.modules.base.base.domiain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "facility_service", schema = "public", catalog = "lamisplus")
public class FacilityService {
    private Long id;
    private String dayService;
    private String timeService;
    private String charge;
    private Long facilityId;
    private Facility facilityByFacilityId;

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
    @Column(name = "day_service", nullable = true, length = -1)
    public String getDayService() {
        return dayService;
    }

    public void setDayService(String dayService) {
        this.dayService = dayService;
    }

    @Basic
    @Column(name = "time_service", nullable = true, length = -1)
    public String getTimeService() {
        return timeService;
    }

    public void setTimeService(String timeService) {
        this.timeService = timeService;
    }

    @Basic
    @Column(name = "charge", nullable = true, length = -1)
    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    @Basic
    @Column(name = "facility_id", nullable = false, insertable = false, updatable = false)
    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacilityService that = (FacilityService) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dayService, that.dayService) &&
                Objects.equals(timeService, that.timeService) &&
                Objects.equals(charge, that.charge) &&
                Objects.equals(facilityId, that.facilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayService, timeService, charge, facilityId);
    }

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", nullable = false)
    public Facility getFacilityByFacilityId() {
        return facilityByFacilityId;
    }

    public void setFacilityByFacilityId(Facility facilityByFacilityId) {
        this.facilityByFacilityId = facilityByFacilityId;
    }
}
