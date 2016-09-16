package com.exist.ecc.person.core.service.input.impl;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.exist.ecc.person.core.dto.AddressDto;
import com.exist.ecc.person.core.dto.NameDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.util.BigDecimalUtil;
import com.exist.ecc.person.util.DateUtil;

public class PersonCsvInputService extends CsvInputService {

  public PersonCsvInputService(Session session) {
    PersonDataService personDataService = new PersonDataService();
    personDataService.setSession(session);
    setDataService(personDataService);
  }

  @Override
  public void processValueArray(String[] values) {
    if (values.length != 14) {
      return;
    }

    Long personId = Long.parseLong(values[0]);

    Date birthDate = DateUtil.parse(values[1]);
    Date dateHired = DateUtil.parse(values[3]);

    BigDecimal gwa = BigDecimalUtil.parse(values[2]);

    boolean employed = values[4].equals("t");

    NameDto nameDto = new NameDto(values[5], values[7], values[6],
                                  values[8], values[9]);

    AddressDto addressDto = new AddressDto(values[10], values[11],  
                                           values[12], values[13]);

    PersonDto personDto = new PersonDto(personId, nameDto, addressDto,
                                        birthDate, dateHired, gwa,
                                        employed, null, null);

    getDataService().save(personDto);
  }

  @Override
  public void clearDatabaseTable() {
    List<PersonDto> personDtos = getDataService().getAll();

    for (PersonDto personDto : personDtos) {
      getDataService().delete(personDto);
    }
  }

}