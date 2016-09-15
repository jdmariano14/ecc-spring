package com.exist.ecc.person.core.dto;

public class ContactDto {

  private long contactId;
  private String contactType;
  private String value;
  private long personId;

  public ContactDto() {
    
  }

  public ContactDto(long contactId,
                    String contactType,
                    String value,
                    long personId)
  {
    setContactId(contactId);
    setContactType(contactType);
    setValue(value);
    setPersonId(personId);
  }

  public long getContactId() {
    return contactId;
  }

  public void setContactId(long contactId) {
    this.contactId = contactId;
  }

  public String getContactType() {
    return contactType;
  }

  public void setContactType(String contactType) {
    this.contactType = contactType;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }
}