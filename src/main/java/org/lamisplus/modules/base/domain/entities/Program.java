package org.lamisplus.modules.base.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lamisplus.modules.base.domain.entities.ServiceEnrollment;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "service", schema = "public", catalog = "lamisplus2")
public class Program {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String programCode;

    @Basic
    @Column(name = "module_id")
    private Long moduleId;

}
