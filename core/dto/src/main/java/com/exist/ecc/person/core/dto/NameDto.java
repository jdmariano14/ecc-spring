package com.exist.ecc.person.core.dto;

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

}