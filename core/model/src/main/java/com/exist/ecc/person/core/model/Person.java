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

  private Collection<Role> role;

}