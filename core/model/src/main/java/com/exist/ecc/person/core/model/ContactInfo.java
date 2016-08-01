package com.exist.ecc.person.core.model;

public abstract class ContactInfo {

  private String info;
  
  public String getInfo() {
    return info;
  }

  public void setInfo(String newInfo) throws IllegalArgumentException {
    if (isValidInfo(newInfo)) {
      info = newInfo;
    } else {
      throw new IllegalArgumentException();
    }
  }

  protected abstract boolean isValidInfo(String info);
}