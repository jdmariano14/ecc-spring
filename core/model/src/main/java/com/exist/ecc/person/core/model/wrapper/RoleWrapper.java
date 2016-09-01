package com.exist.ecc.person.core.model.wrapper;

import java.util.Collection;
import java.util.List;
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

  public static List<RoleWrapper> wrapCollection(
    Collection<Role> collection) 
  {
    List<RoleWrapper> roles = 
      collection.stream()
                .map(r -> new RoleWrapper(r))
                .collect(Collectors.toList());

    return roles;
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