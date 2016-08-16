package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Contact;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class ComposedContactFormatter implements OutputFormatter<Contact> {

  private int parentIndent;

  public ComposedContactFormatter(int parentIndent) {
    this.parentIndent = parentIndent;
  }

  public String format(Contact contact) {
    String contactType = contact.getClass().getSimpleName();

    String roleString = 
      new StringBuilder()
      .append(indent())
      .append(StringUtil.formatUnlessBlank("%s: ", contactType))
      .append(StringUtil.formatUnlessBlank("%s", contact.getInfo()))
      .toString();

    return roleString;
  }

  private String indent() {
    return StringUtil.spaces(parentIndent + 2);
  }

}