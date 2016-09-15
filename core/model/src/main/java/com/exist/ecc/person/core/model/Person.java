package com.exist.ecc.person.core.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.exist.ecc.person.core.dto.PersonDto;

@Entity
@Table(name = "PERSON")
public class Person {

  @Id
  @GeneratedValue
  @Column(name = "PERSON_ID")
  private long personId;

  @Embedded
  private Name name;

  @Embedded
  private Address address;

  @Temporal(value = TemporalType.DATE)
  @Column(name = "BIRTH_DATE")
  private Date birthDate;

  @Temporal(value = TemporalType.DATE)
  @Column(name = "DATE_HIRED")
  private Date dateHired;

  @Min(1)
  @Max(5)
  @Column(name = "GWA")
  private BigDecimal gwa;

  @Column(name = "EMPLOYED")
  private boolean employed;
  
  @OneToMany(cascade = CascadeType.ALL,
             mappedBy = "person")
  private Set<Contact> contacts;

  @ManyToMany
  @JoinTable(name = "PERSON_ROLE",
             joinColumns = @JoinColumn(name = "PERSON_ID"), 
             inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  private Set<Role> roles;

  public long getPersonId() { return personId; }
  public Name getName() { return name; }
  public Address getAddress() { return address; }
  public Date getBirthDate() { return birthDate; }
  public Date getDateHired() { return dateHired; }
  public BigDecimal getGwa() { return gwa; }
  public boolean isEmployed() { return employed; }
  public Set<Contact> getContacts() { return contacts; }
  public Set<Role> getRoles() { return roles; }

  public void setName(Name name) {
    this.name = name;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
  
  public PersonDto getDto() {
    return new PersonDto(this.getPersonId(),
                         this.getName().getDto(),
                         this.getAddress().getDto(),
                         this.getBirthDate(),
                         this.getDateHired(),
                         this.getGwa(),
                         this.isEmployed(),
                         this.getContactIds(),
                         this.getRoleIds());
  }

  public void readDto(PersonDto dto) {
    this.name.readDto(dto.getName());
    this.address.readDto(dto.getAddress());
    this.birthDate = dto.getBirthDate();
    this.dateHired = dto.getDateHired();
    this.gwa = dto.getGwa();
    this.employed = dto.isEmployed();/*
    this.contacts = dto.getContactIds().stream()
                       .map(cid -> contactService.get(cid))
                       .collect(Collectors.toSet());
    this.roles = dto.getRoleIds().stream()
                    .map(role -> roleService.get(rid))
                    .collect(Collectors.toSet());*/
  }

  private Set<Long> getContactIds() {
    return contacts.stream()
                   .map(c -> c.getContactId())
                   .collect(Collectors.toSet());
  }

  private Set<Long> getRoleIds() {
    return roles.stream()
                .map(r -> r.getRoleId())
                .collect(Collectors.toSet());
  }
}