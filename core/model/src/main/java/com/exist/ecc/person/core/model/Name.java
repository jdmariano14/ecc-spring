package com.exist.ecc.person.core.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

import com.exist.ecc.person.core.dto.NameDto;

@Embeddable
public class Name {
  
  @NotBlank
  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "MIDDLE_NAME")
  private String middleName;

  @NotBlank
  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "SUFFIX")
  private String suffix;

  @Column(name = "TITLE")
  private String title;
  
  public String getFirstName() { return firstName; }
  public String getMiddleName() { return middleName; }
  public String getLastName() { return lastName; }
  public String getSuffix() { return suffix; }
  public String getTitle() { return title; }

  public NameDto getDto() {
    return new NameDto(this.getFirstName(),
                       this.getMiddleName(),
                       this.getLastName(),
                       this.getSuffix(),
                       this.getTitle());
  }

  public void readDto(NameDto dto) {
    this.firstName = dto.getFirstName();
    this.middleName = dto.getMiddleName();
    this.lastName = dto.getLastName();
    this.suffix = dto.getSuffix();
    this.title = dto.getTitle();
  }


}