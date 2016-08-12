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
    OutputFormatter nameFormatter = new NameOutputFormatter();
    OutputFormatter addressFormatter = new AddressOutputFormatter();
    String idString = String.valueOf(person.getPersonId());
    String nameString = nameFormatter.format(person.getName());
    String addressString = addressFormatter.format(person.getAddress());
    String gwaString = person.getGwa().toString();
    String employedString = person.isEmployed() ? "Yes" : "No";
    DateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    String birthDateString = dateFormat.format(person.getBirthDate());
    String dateHiredString = dateFormat.format(person.getDateHired());

    String personString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessEmpty(idString, "[%s] "))
      .append(StringUtil.formatUnlessEmpty(
        nameString, "%s" + System.lineSeparator()))
      .append(StringUtil.formatUnlessEmpty(
        addressString, "  Address: %s" + System.lineSeparator()))
      .append(StringUtil.formatUnlessEmpty(
        birthDateString, "  Birth date:  %s" + System.lineSeparator()))
      .append(StringUtil.formatUnlessEmpty(
        dateHiredString, "  Date hired:  %s" + System.lineSeparator()))
      .append(StringUtil.formatUnlessEmpty(
        gwaString, "  GWA:  %s" + System.lineSeparator()))
      .append(StringUtil.formatUnlessEmpty(
        employedString, "  Employed:  %s" + System.lineSeparator()))
      .toString();

    return personString;
  }

}