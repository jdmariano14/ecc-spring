package com.exist.ecc.person.core.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;

public class Person {

  private long personId;

  private Name name;

  private Address address;

  private Date birthDate;

  private BigDecimal gwa;

  private Date dateHired;

  private boolean currentlyEmployed;

  private Collection<ContactInfo> contactInfo;

  private Collection<Role> roles;

  public long getPersonId() {
    return PersonId;
  }

  public void setPersonId(long newPersonId) {
    personId = newPersonId;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name newName) {
    name = newName;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address newAddress) {
    address = newAddress;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date newBirthDate) {
    birthDate = newBirthDate;
  }

  public BigDecimal getGwa() {
    return gwa;
  }

  public void setGwa(BigDecimal newGwa) {
    gwa = newGwa;
  }

  public Date getDateHired() {
    return dateHired;
  }

  public void setDateHired(Date newDateHired) {
    dateHired = newDateHired;
  }

  public boolean getCurrentlyEmployed() {
    return currentlyEmployed;
  }

  public void setCurrentlyEmployed(boolean newCurrentlyEmployed) {
    currentlyEmployed = newCurrentlyEmployed;
  }

  public Collection<ContactInfo> getContactInfo() {
    return contactInfo;
  }

  public void setContactInfo(Collection<ContactInfo> newContactInfo) {
    contactInfo = newContactInfo;
  }

  public Collection<Role> getRole() {
    return roles;
  }

  public void setRole(Collection<Role> newRole) {
    roles = newRole;
  }
}