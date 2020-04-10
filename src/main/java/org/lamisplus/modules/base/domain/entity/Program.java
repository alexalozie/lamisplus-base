package org.lamisplus.modules.base.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "program", schema = "public", catalog = "lamisplus")
public class Program implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;


    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "module_id")
    private Long moduleId;

   /* @OneToMany(mappedBy = "programByProgramCode")
    private Collection<Encounter> programByProgramCode;
*/

   /* @OneToMany(mappedBy = "formByProgramCode")
    private Collection<Form> formByProgramCode;

*/
    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Module moduleByModuleId;}
