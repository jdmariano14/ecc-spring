package com.exist.ecc.person.core.model;

import java.util.Collection;

public class Role {

  private long roleId;
  
  private String name;

  private Collection<Person> persons;

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long newRoleId) {
    roleId = newRoleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String newName) {
    name = newName;
  }

  public Collection<Person> getPersons() {
    return persons;
  }

  public void setRoles(Collection<Person> newPersons) {
    persons = newPersons;
  }
}