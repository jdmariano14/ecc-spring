package com.exist.ecc.person.core.model;

public abstract class ContactDetail {

  private long contactDetailId;

  private String detail;

  private Person person;

  public String getDetail() {
    return detail;
  }

  public void setDetail(String newDetail) throws IllegalArgumentException {
    if (isValidDetail(newDetail)) {
      detail = newDetail;
    } else {
      throw new IllegalArgumentException();
    }
  }

  protected abstract boolean isValidDetail(String detail);
}