package com.exist.ecc.person.core.model;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import com.exist.ecc.person.core.dto.RoleDto;

@Entity
@Table(name = "ROLE")
public class Role extends OverridableIdModel<Long> {

  @Id
  @GenericGenerator(name="overridable",
                    strategy="com.exist.ecc.person.core.model.generator.OverridableSequenceGenerator")
  @GeneratedValue(generator = "overridable")
  @Column(name = "ROLE_ID")
  private long roleId;
  
  @NotBlank
  @Column(name = "NAME")
  private String name;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
              mappedBy = "roles")
  private Set<Person> persons;

  public long getRoleId() { return roleId; }
  public String getName() { return name; }
  public Set<Person> getPersons() { return persons; }
  
  public RoleDto getDto() {
    return new RoleDto(this.getRoleId(),
                       this.getName(),
                       this.getPersonIds());
  }

  public void readDto(RoleDto dto) {
    long dtoId = dto.getRoleId();

    if (dtoId > 0) {
      setOverrideId(dtoId);
    }

    this.name = dto.getName();
  }

  private Set<Long> getPersonIds() {
    return persons.stream()
                  .map(p -> p.getPersonId())
                  .collect(Collectors.toSet());
  }
  
}