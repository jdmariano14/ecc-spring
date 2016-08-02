package com.exist.ecc.person.core.model;

public abstract class ContactDetail {

  private long contactDetailId;

  private String detail;

  private Person person;

  public long getContactDetailId() {
    return contactDetailId;
  }

  public void setContactDetailId(long newContactDetailId) {
    contactDetailId = newContactDetailId;
  }

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

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person newPerson) {
    person = newPerson;
  }

  protected abstract boolean isValidDetail(String detail);
}