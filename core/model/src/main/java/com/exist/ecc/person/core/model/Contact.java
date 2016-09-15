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

import com.exist.ecc.person.core.dto.ContactDto;

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

  public long getContactId() { return contactId;}
  public String getInfo() { return info; }
  public Person getPerson() { return person; }

  public ContactDto getDto() {
    return new ContactDto(this.getContactId(),
                          this.getContactType(),
                          this.getInfo(),
                          this.getPersonId());
  }

  public void readDto(ContactDto dto) {
    this.info = dto.getValue();
    //this.person = personService.get(dto.getContactId());
  }

  private String getContactType() {
    return this.getClass().getSimpleName();
  }

  private long getPersonId() {
    return this.getPerson().getPersonId();
  }

}