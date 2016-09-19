package com.exist.ecc.person.core.service.input.impl;

import java.util.List;

import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;

public class PersonRoleCsvInputService extends CsvInputService {
  
  private PersonDataService personDataService;

  public PersonRoleCsvInputService(PersonDataService personDataService) {
    this.personDataService = personDataService;
  }

  @Override
  public void processValueArray(String[] values) {
    if (values.length != 2) {
      return;
    }

    Long personId = Long.parseLong(values[0]);
    Long roleId = Long.parseLong(values[1]);
    
    PersonDto personDto = personDataService.get(personId);
    personDto.getRoleIds().add(roleId);
    personDataService.save(personDto);
  }

  @Override
  public void clearDatabaseTable() {
    List<PersonDto> personDtos = personDataService.getAll();

    for (PersonDto personDto : personDtos) {
      personDto.getRoleIds().clear();
      personDataService.save(personDto);
    }
  }

}