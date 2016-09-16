package com.exist.ecc.person.core.dto;

import com.exist.ecc.person.util.StringUtil;

public class NameDto {
  
  private String firstName;
  private String middleName;
  private String lastName;
  private String suffix;
  private String title;

  public NameDto() {
    
  }

  public NameDto(String firstName,
                 String middleName,
                 String lastName,
                 String suffix,
                 String title)
  {
    setFirstName(firstName);
    setMiddleName(middleName);
    setLastName(lastName);
    setSuffix(suffix);
    setTitle(title);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShortName() {
    String nameString = 
      StringUtil.formatUnlessBlank("%s ", getFirstName())
      + StringUtil.formatUnlessBlank("%s", getLastName());

    return nameString;
  }

  public String getFormalShortName() {
    String nameString = 
      StringUtil.formatUnlessBlank("%s, ", getLastName())
      + StringUtil.formatUnlessBlank("%s", getFirstName());

    return nameString;
  }

  public String getFullName() {
    String nameString = 
      StringUtil.formatUnlessBlank("%s ", getTitle())
      + StringUtil.formatUnlessBlank("%s ", getFirstName())
      + StringUtil.formatUnlessBlank("%s ", getMiddleName())
      + StringUtil.formatUnlessBlank("%s", getLastName())
      + StringUtil.formatUnlessBlank(", %s", getSuffix());

    return nameString;
  }

  @Override
  public String toString() {
    return getFullName();
  }

}