package com.exist.ecc.person.core.service.output.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.Locale;

import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.CollectionFormatter;

import com.exist.ecc.person.util.StringUtil;

public class PersonFormatter implements OutputFormatter<Person> {

  private int indentWidth;

  public String format(Person person) {
    indentWidth = 
      StringUtil.formatUnlessBlank("[%d] ", person.getPersonId()).length();

    OutputFormatter<Name> nameFormatter = new NameFormatter();
    OutputFormatter<Address> addressFormatter = new AddressFormatter();
    CollectionFormatterImpl<Role> rolesFormatter =
      new CollectionFormatterImpl<Role>(new ComposedRoleFormatter());
    CollectionFormatterImpl<Contact> contactsFormatter =
      new CollectionFormatterImpl<Contact>(
        new ComposedContactFormatter(indentWidth));
    
    String nameString = nameFormatter.format(person.getName());
    String addressString = addressFormatter.format(person.getAddress());
    String employedString = person.isEmployed() ? "Yes" : "No";
    String birthDateString;
    String dateHiredString;
    String contactsString;
    String rolesString;

    DateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    try {
      birthDateString = dateFormat.format(person.getBirthDate()); 
    } catch (NullPointerException e) {
      birthDateString = "";
    }

    try {
      dateHiredString = dateFormat.format(person.getDateHired());
    } catch (NullPointerException e) {
      dateHiredString = "";
    }

    contactsFormatter.setDelimiter(System.lineSeparator());
    contactsString = contactsFormatter.format(person.getContacts());

    rolesFormatter.setDelimiter(", ");
    rolesString = rolesFormatter.format(person.getRoles());

    String personString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("[%d] ", person.getPersonId()))
      .append(StringUtil.formatUnlessBlank("%s", nameString))
      .append(StringUtil.formatUnlessBlank(
        onNewLine("Address:  %s"), addressString))
      .append(StringUtil.formatUnlessBlank(
        onNewLine("Birth date:  %s"), birthDateString))
      .append(StringUtil.formatUnlessBlank(
        onNewLine("Date hired:  %s"), dateHiredString))
      .append(StringUtil.formatUnlessBlank(
        onNewLine("GWA:  %s"), person.getGwa()))
      .append(StringUtil.formatUnlessBlank(
        onNewLine("Employed:  %s"), employedString))
      .append(StringUtil.formatUnlessBlank(
        onNewLine("Contacts:" + StringUtil.lineBreaks(1) + "%s", 2),
        contactsString))
      .append(StringUtil.formatUnlessBlank(
        onNewLine("Roles:  %s", 2), rolesString))
      .toString();

    return personString;
  }

  private String indent() {
    return StringUtil.spaces(indentWidth);
  }

  private String onNewLine(String str) {
    return onNewLine(str, 1);
  }

  private String onNewLine(String str, int lineBreaks) {
    return new StringBuilder()
           .append(StringUtil.lineBreaks(lineBreaks))
           .append(indent())
           .append(str)
           .toString();
  }

}