package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Contact;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class ContactFormatter implements OutputFormatter<Contact> {

  public String format(Contact contact) {
    String contactType = contact.getClass().getSimpleName();

    String roleString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("[%d] ", contact.getContactId()))
      .append(StringUtil.formatUnlessBlank("%s: ", contactType))
      .append(StringUtil.formatUnlessBlank("%s", contact.getInfo()))
      .toString();

    return roleString;
  }

}