package com.exist.ecc.person.core.dto;

import java.util.HashSet;
import java.util.Set;

public class RoleDto {

  private long roleId;
  private String name;
  private Set<Long> personIds;

  public RoleDto() {
    personIds = new HashSet();
  }

  public RoleDto(long roleId,
                 String name,
                 Set<Long> personIds)
  {
    setRoleId(roleId);
    setName(name);
    setPersonIds(personIds);
  }

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Long> getPersonIds() {
    return personIds;
  }

  public void setPersonIds(Set<Long> personIds) {
    this.personIds = personIds;
  }
  
}