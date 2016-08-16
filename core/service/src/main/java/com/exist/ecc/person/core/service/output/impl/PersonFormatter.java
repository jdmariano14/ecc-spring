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

  public static final int INDENT = 5;

  public String format(Person person) {
    OutputFormatter<Name> nameFormatter = new NameFormatter();
    OutputFormatter<Address> addressFormatter = new AddressFormatter();
    CollectionFormatterImpl<Role> rolesFormatter =
      new CollectionFormatterImpl<Role>(new ComposedRoleFormatter());
    CollectionFormatterImpl<Contact> contactsFormatter =
      new CollectionFormatterImpl<Contact>(
        new ComposedContactFormatter(INDENT));
    
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
      .append(StringUtil.formatUnlessBlank("%s" + lineBreak(), nameString))
      .append(StringUtil.formatUnlessBlank(
        indent() + "Address:  %s" + lineBreak(), addressString))
      .append(StringUtil.formatUnlessBlank(
        indent() + "Birth date:  %s" + lineBreak(), birthDateString))
      .append(StringUtil.formatUnlessBlank(
        indent() + "Date hired:  %s" + lineBreak(), dateHiredString))
      .append(StringUtil.formatUnlessBlank(
        indent() + "GWA:  %s" + lineBreak(), person.getGwa()))
      .append(StringUtil.formatUnlessBlank(
        indent() + "Employed:  %s" + lineBreak(), employedString))
      .append(StringUtil.formatUnlessBlank(
        lineBreak() + indent() + "Contacts:" + lineBreak() + "%s",
        contactsString))
      .append(StringUtil.formatUnlessBlank(
        lineBreak() + indent() + "Roles:  %s" + lineBreak(), rolesString))
      .toString();

    return personString;
  }

  private String indent() {
    return StringUtil.spaces(INDENT);
  }

  private String lineBreak() {
    return System.lineSeparator();
  }

}