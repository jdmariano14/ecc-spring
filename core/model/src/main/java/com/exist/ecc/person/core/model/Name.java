package com.exist.ecc.person.core.model;

import org.apache.commons.lang3.StringUtils;

public class Name {

  private String firstName;

  private String lastName;

  private String middleName;

  private String suffix;

  private String title;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String newFirstName) {
    firstName = newFirstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String newLastName) {
    lastName = newLastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String newMiddleName) {
    middleName = newMiddleName;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String newSuffix) {
    suffix = newSuffix;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String newTitle) {
    title = newTitle;
  }

  @Override
  public String toString() {
    String nameString = new StringBuilder()
                        .append(propertyToString(title, false))
                        .append(propertyToString(firstName, false))
                        .append(propertyToString(middleName, false))
                        .append(lastName)
                        .append(propertyToString(suffix, true))
                        .toString();

    return nameString;
  }

  private String propertyToString(String property, boolean spacePrefix) {
    return StringUtils.isEmpty(property) 
           ? new String()
           : spacePrefix
             ? String.format(" %s", property)
             : String.format("%s ", property);
  }

}