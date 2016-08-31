package com.exist.ecc.person.core.model.wrapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.util.StringUtil;

public class RoleWrapper {
  private final Role role;

  public RoleWrapper(Role role) {
    this.role = role;
  }

  public long getRoleId() {
    return role.getRoleId();
  }

  public String getName() {
    return role.getName();
  }

  public Set<PersonWrapper> getPersons() {
    Set<PersonWrapper> persons = 
      role.getPersons().stream()
          .map(p -> new PersonWrapper(p))
          .collect(Collectors.toSet());

    return persons;
  }

}