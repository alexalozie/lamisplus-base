package org.lamisplus.modules.base.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lamisplus.modules.base.domain.entities.ServiceEnrollment;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "service", schema = "public", catalog = "lamisplus")
public class Service {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String serviceName;

    @Basic
    @Column(name = "module_id")
    private Long moduleId;

    @OneToMany(mappedBy = "serviceByServicesId")
    public Collection<ServiceEnrollment> ServiceEnrollmentsById;

}
