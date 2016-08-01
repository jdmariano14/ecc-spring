package com.exist.ecc.person.core.model;

public class Role {

  private long roleId;
  
  private String name;

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

}