package com.exist.ecc.person.core.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CONTACT_TYPE")
@Table(name = "CONTACT")
public class Contact {

  @Id
  @GeneratedValue
  @Column(name = "CONTACT_ID")
  private long contactId;

  @NotBlank
  @Column(name = "INFO")
  private String info;

  @ManyToOne
  @JoinColumn(name = "PERSON_ID")
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