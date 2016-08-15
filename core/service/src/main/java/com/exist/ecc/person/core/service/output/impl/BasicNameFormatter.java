package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Name;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class BasicNameFormatter implements OutputFormatter<Name> {

  public String format(Name name) {
    String nameString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("%s ", name.getTitle()))
      .append(StringUtil.formatUnlessBlank("%s", name.getLastName()))
      .toString();

    return nameString;
  }

}