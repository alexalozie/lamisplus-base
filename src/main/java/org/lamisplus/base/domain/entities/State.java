package org.lamisplus.base.domain.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class State {
    private Long id;
    private String name;
    private Long countryId;
    //private Collection<PersonContact> personContactsById;
    //private Collection<Province> provincesById;
    private Country countryByCountryId;

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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "country_id", nullable = false, insertable = false, updatable = false)
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(id, state.id) &&
                Objects.equals(name, state.name) &&
                Objects.equals(countryId, state.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryId);
    }

    /*
    @OneToMany(mappedBy = "stateByStateId")
    public Collection<PersonContact> getPersonContactsById() {
        return personContactsById;
    }

    public void setPersonContactsById(Collection<PersonContact> personContactsById) {
        this.personContactsById = personContactsById;
    }

    @OneToMany(mappedBy = "stateByStateId")
    public Collection<Province> getProvincesById() {
        return provincesById;
    }

    public void setProvincesById(Collection<Province> provincesById) {
        this.provincesById = provincesById;
    }

     */

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Country getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(Country countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }
}
