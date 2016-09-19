package com.exist.ecc.person.core.service.build;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import com.exist.ecc.person.core.dto.AddressDto;
import com.exist.ecc.person.core.dto.NameDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.util.BigDecimalUtil;
import com.exist.ecc.person.util.DateUtil;

public class PersonBuildService {

  public static class PersonDtoBuilder {

    private String personId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String title;
    private String streetAddress;
    private String barangay;
    private String municipality;
    private String zipCode;
    private String birthDate;
    private String dateHired;
    private String gwa;
    private String employed;

    public PersonDtoBuilder setPersonId(String personId) {
      this.personId = personId;
      return this;
    }

    public PersonDtoBuilder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public PersonDtoBuilder setMiddleName(String middleName) {
      this.middleName = middleName;
      return this;
    }

    public PersonDtoBuilder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public PersonDtoBuilder setSuffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

    public PersonDtoBuilder setTitle(String title) {
      this.title = title;
      return this;
    }

    public PersonDtoBuilder setStreetAddress(String streetAddress) {
      this.streetAddress = streetAddress;
      return this;
    }

    public PersonDtoBuilder setBarangay(String barangay) {
      this.barangay = barangay;
      return this;
    }

    public PersonDtoBuilder setMunicipality(String municipality) {
      this.municipality = municipality;
      return this;
    }

    public PersonDtoBuilder setZipCode(String zipCode) {
      this.zipCode = zipCode;
      return this;
    }

    public PersonDtoBuilder setBirthDate(String birthDate) {
      this.birthDate = birthDate;
      return this;
    }

    public PersonDtoBuilder setDateHired(String dateHired) {
      this.dateHired = dateHired;
      return this;
    }

    public PersonDtoBuilder setGwa(String gwa) {
      this.gwa = gwa;
      return this;
    }

    public PersonDtoBuilder setEmployed(String employed) {
      this.employed = employed;
      return this;
    }

    public PersonDto build() {
      long _personId = -1; 
      
      try {
        _personId = Long.parseLong(personId);
      } catch (NullPointerException | NumberFormatException e) {

      }

      Date _birthDate = DateUtil.parse(birthDate);
      Date _dateHired = DateUtil.parse(dateHired);

      BigDecimal _gwa = BigDecimalUtil.parse(gwa);

      boolean _employed = employed.equals("t");

      NameDto nameDto = new NameDto(firstName, middleName, lastName,
                                    suffix, title);

      AddressDto addressDto = new AddressDto(streetAddress, barangay,
                                             municipality, zipCode);

      return new PersonDto(_personId, nameDto, addressDto,
                           _birthDate, _dateHired, _gwa,
                           _employed, null, null);
    }

  }

  public PersonDtoBuilder getDtoBuilder() {
    return new PersonDtoBuilder();
  }

}