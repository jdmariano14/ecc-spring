package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Person;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.CollectionFormatter;

import com.exist.ecc.person.util.StringUtil;

public class BasicPersonFormatter implements OutputFormatter<Person> {

  public String format(Person person) {
    OutputFormatter<Name> nameFormatter = new BasicNameFormatter();
    
    String nameString = nameFormatter.format(person.getName());

    String personString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("%s", nameString))
      .toString();

    return personString;
  }


}