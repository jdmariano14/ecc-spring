package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Name;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class NameOutputFormatter implements OutputFormatter<Name> {

  public String format(Name name) {
    String nameString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessEmpty(name.getTitle(), "%s "))
      .append(StringUtil.formatUnlessEmpty(name.getFirstName(), "%s "))
      .append(StringUtil.formatUnlessEmpty(name.getMiddleName(), "%s "))
      .append(StringUtil.formatUnlessEmpty(name.getLastName(), "%s"))
      .append(StringUtil.formatUnlessEmpty(name.getSuffix(), ", %s"))
      .toString();

    return nameString;
  }

}