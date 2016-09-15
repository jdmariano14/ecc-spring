package com.exist.ecc.person.core.dto;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Set;

public class PersonDto {

  private long personId;
  private NameDto name;
  private AddressDto address;
  private Date birthDate;
  private Date dateHired;
  private BigDecimal gwa;
  private boolean employed;
  private Set<Long> contactIds;
  private Set<Long> roleIds;

  public PersonDto(long personId,
                   NameDto name,
                   AddressDto address,
                   Date birthDate,
                   Date dateHired,
                   BigDecimal gwa,
                   boolean employed,
                   Set<Long> contactIds,
                   Set<Long> roleIds)
  {
    setPersonId(personId);
    setName(name);
    setAddress(address);
    setBirthDate(birthDate);
    setDateHired(dateHired);
    setGwa(gwa);
    setEmployed(employed);
    setContactIds(contactIds);
    setRoleIds(roleIds);
  }

  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }

  public NameDto getName() {
    return name;
  }

  public void setName(NameDto name) {
    this.name = name;
  }

  public AddressDto getAddress() {
    return address;
  }

  public void setAddress(AddressDto address) {
    this.address = address;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public Date getDateHired() {
    return dateHired;
  }

  public void setDateHired(Date dateHired) {
    this.dateHired = dateHired;
  }

  public BigDecimal getGwa() {
    return gwa;
  }

  public void setGwa(BigDecimal gwa) {
    this.gwa = gwa;
  }

  public boolean isEmployed() {
    return employed;
  }

  public void setEmployed(boolean employed) {
    this.employed = employed;
  }

  public Set<Long> getContactIds() {
    return contactIds;
  }

  public void setContactIds(Set<Long> contactIds) {
    this.contactIds = contactIds;
  }

  public Set<Long> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(Set<Long> roleIds) {
    this.roleIds = roleIds;
  }
}