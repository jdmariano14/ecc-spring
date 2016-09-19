package com.exist.ecc.person.core.service.input.impl;

import java.util.List;

import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.core.service.build.PersonBuildService;

public class PersonCsvInputService extends CsvInputService {
  
  private PersonDataService personDataService;
  private PersonBuildService personBuildService;

  public PersonCsvInputService(PersonDataService personDataService,
                               PersonBuildService personBuildService) 
  {
    this.personDataService = personDataService;
    this.personBuildService = personBuildService;
  }

  @Override
  public void processValueArray(String[] values) {
    if (values.length != 14) {
      return;
    }

    PersonDto personDto = 
      personBuildService.getDtoBuilder()
                        .setPersonId(values[0])
                        .setBirthDate(values[1])
                        .setGwa(values[2])
                        .setDateHired(values[3])
                        .setEmployed(values[4])
                        .setFirstName(values[5])
                        .setLastName(values[6])
                        .setMiddleName(values[7])
                        .setSuffix(values[8])
                        .setTitle(values[9])
                        .setStreetAddress(values[10])
                        .setBarangay(values[11])
                        .setMunicipality(values[12])
                        .setZipCode(values[13])
                        .build();

    personDataService.save(personDto);
  }

  @Override
  public void clearDatabaseTable() {
    List<PersonDto> personDtos = personDataService.getAll();

    for (PersonDto personDto : personDtos) {
      personDataService.delete(personDto);
    }
  }

}