package com.exist.ecc.person.core.service.output.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.Locale;

import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.CollectionFormatter;

import com.exist.ecc.person.util.StringUtil;

public class PersonFormatter implements OutputFormatter<Person> {

  public String format(Person person) {
    OutputFormatter<Name> nameFormatter = new NameFormatter();
    OutputFormatter<Address> addressFormatter = new AddressFormatter();
    CollectionFormatter<Role> rolesFormatter =
      new CollectionFormatterImpl<Role>(new RoleFormatter());
    
    String nameString = nameFormatter.format(person.getName());
    String addressString = addressFormatter.format(person.getAddress());
    String employedString = person.isEmployed() ? "Yes" : "No";
    String rolesString = rolesFormatter.format(person.getRoles());
    String birthDateString;
    String dateHiredString;

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
        lineBreak() + indent() + "Roles:  %s" + lineBreak(), rolesString))
      .toString();

    return personString;
  }

  private String indent() {
    return "     ";
  }
  private String lineBreak() {
    return System.lineSeparator();
  }

}