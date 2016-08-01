package com.exist.ecc.person.core.model;

public class Person {

  private long personId;

  private Name name;

  private Address address;

  private Date birthDate;

  private BigDecimal gwa;

  private Date dateHired;

  private boolean currentlyEmployed;

  private Collection<ContactInfo> contactInfo;

  private Collection<Role> role;

}