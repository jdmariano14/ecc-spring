package com.exist.ecc.person.core.model.wrapper;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.exist.ecc.person.core.model.Role;
import com.exist.ecc.person.core.model.Person;

public class PersonWrapper {
  private final Person person;

  public PersonWrapper(Person person) {
    this.person = person;
  }

  public static List<PersonWrapper> wrapCollection(
    Collection<Person> collection) 
  {
    List<PersonWrapper> persons = 
      collection.stream()
                .map(p -> new PersonWrapper(p))
                .collect(Collectors.toList());

    return persons;
  }

  public long getPersonId() {
    return person.getPersonId();
  }

  public NameWrapper getName() {
    return new NameWrapper(person.getName());
  }

  public AddressWrapper getAddress() {
    return new AddressWrapper(person.getAddress());
  }

  public Date getBirthDate() {
    return person.getBirthDate();
  }

  public Date getDateHired() {
    return person.getDateHired();
  }

  public BigDecimal getGwa() {
    return person.getGwa();
  }

  public boolean isEmployed() {
    return person.isEmployed();
  }

  public Set<ContactWrapper> getContacts() {
    Set<ContactWrapper> contacts = 
      person.getContacts().stream()
            .map(c -> new ContactWrapper(c))
            .collect(Collectors.toSet());

    return contacts;
  }

  public Set<RoleWrapper> getRoles() {
    Set<RoleWrapper> roles = 
      person.getRoles().stream()
            .map(r -> new RoleWrapper(r))
            .collect(Collectors.toSet());

    return roles;
  }
}