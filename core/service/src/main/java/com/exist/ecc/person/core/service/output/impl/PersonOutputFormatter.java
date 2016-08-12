package com.exist.ecc.person.core.service.output.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.Locale;

import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Person;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class PersonOutputFormatter implements OutputFormatter<Person> {

  public String format(Person person) {
    OutputFormatter<Name> nameFormatter = new NameOutputFormatter();
    OutputFormatter<Address> addressFormatter = new AddressOutputFormatter();
    String nameString = nameFormatter.format(person.getName());
    String addressString = addressFormatter.format(person.getAddress());
    String employedString = person.isEmployed() ? "Yes" : "No";
    DateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    String birthDateString = dateFormat.format(person.getBirthDate());
    String dateHiredString = dateFormat.format(person.getDateHired());

    String personString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("[%d] ", person.getPersonId()))
      .append(StringUtil.formatUnlessBlank(
        "%s" + System.lineSeparator(), nameString))
      .append(StringUtil.formatUnlessBlank(
        "  Address:  %s" + System.lineSeparator(), addressString))
      .append(StringUtil.formatUnlessBlank(
        "  Birth date:  %s" + System.lineSeparator(), birthDateString))
      .append(StringUtil.formatUnlessBlank(
        "  Date hired:  %s" + System.lineSeparator(), dateHiredString))
      .append(StringUtil.formatUnlessBlank(
        "  GWA:  %s" + System.lineSeparator(), person.getGwa()))
      .append(StringUtil.formatUnlessBlank(
        "  Employed:  %s" + System.lineSeparator(), employedString))
      .toString();

    return personString;
  }

}