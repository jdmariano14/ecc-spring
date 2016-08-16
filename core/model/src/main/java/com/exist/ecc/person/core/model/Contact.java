package com.exist.ecc.person.core.model;

public class Contact {

  private long contactId;

  private String info;

  private Person person;

  public long getContactId() {
    return contactId;
  }

  public void setContactId(long newContactId) {
    contactId = newContactId;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String newInfo) {
    info = newInfo;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person newPerson) {
    person = newPerson;
  }

}