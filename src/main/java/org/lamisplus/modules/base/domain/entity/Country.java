package org.lamisplus.modules.base.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "countryByCountryId")
    @JsonIgnore
    public Collection<State> countryByCountryId;
}
