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

import org.hibernate.validator.constraints.NotBlank;

import com.exist.ecc.person.core.dto.RoleDto;

@Entity
@Table(name = "ROLE")
public class Role {

  @Id
  @GeneratedValue
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
    this.name = dto.getName();/*
    this.persons = dto.getPersonIds().stream()
                      .map(pid -> personService.get(pid))
                      .collect(Collectors.toSet());*/
  }
/*
  move association retrieval to service!
  
  public void <T, ID> updateSet(Set<T> oldSet,
                                Set<ID> newSet,
                                Function<T, ID> idMethod,
                                Dao<T, ID> dao) 
  {
    Set<ID> oldIdSet = oldSet.stream()
                             .map(idMethod)
                             .collect(Collectors.toSet());

    Set<ID> addedIdSet = newSet.removeAll(oldSet);
    Set<ID> removedIdSet = oldSet.removeAll(newSet);

    dao.get(addedIdSet)

  }
*/
  private Set<Long> getPersonIds() {
    return persons.stream()
                  .map(p -> p.getPersonId())
                  .collect(Collectors.toSet());
  }
  
}