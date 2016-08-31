package com.exist.ecc.person.core.model.wrapper;

import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Person;

public class ContactWrapper {
  private final Contact contact;

  public ContactWrapper(Contact contact) {
    this.contact = contact;
  }

  public long getContactId() {
    return contact.getContactId();
  }

  public String getInfo() {
    return contact.getInfo();
  }

  public PersonWrapper getPerson() {
    return new PersonWrapper(contact.getPerson());
  }

  public String getType() {
    return contact.getClass().getSimpleName();
  }
}