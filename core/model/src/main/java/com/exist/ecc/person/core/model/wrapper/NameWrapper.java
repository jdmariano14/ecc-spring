package com.exist.ecc.person.core.model.wrapper;

import com.exist.ecc.person.core.model.Name;

import com.exist.ecc.person.util.StringUtil;

public class NameWrapper {
  private final Name name;

  public NameWrapper(Name name) {
    this.name = name;
  }

  public String getLastName() {
    return name.getLastName();
  }

  public String getFirstName() {
    return name.getFirstName();
  }

  public String getMiddleName() {
    return name.getMiddleName();
  }

  public String getSuffix() {
    return name.getSuffix();
  }

  public String getTitle() {
    return name.getTitle();
  }

  public String getShortName() {
    String nameString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("%s, ", name.getLastName()))
      .append(StringUtil.formatUnlessBlank("%s", name.getFirstName()))
      .toString();

    return nameString;
  }

  public String getFullName() {
    String nameString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("%s ", name.getTitle()))
      .append(StringUtil.formatUnlessBlank("%s ", name.getFirstName()))
      .append(StringUtil.formatUnlessBlank("%s ", name.getMiddleName()))
      .append(StringUtil.formatUnlessBlank("%s", name.getLastName()))
      .append(StringUtil.formatUnlessBlank(", %s", name.getSuffix()))
      .toString();

    return nameString;
  }
}