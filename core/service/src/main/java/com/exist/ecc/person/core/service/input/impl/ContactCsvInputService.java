package com.exist.ecc.person.core.service.input.impl;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.exist.ecc.person.core.dto.ContactDto;
import com.exist.ecc.person.core.service.data.impl.ContactDataService;
import com.exist.ecc.person.util.BigDecimalUtil;
import com.exist.ecc.person.util.DateUtil;

public class ContactCsvInputService extends CsvInputService {

  ContactDataService contactDataService = new ContactDataService();

  public ContactCsvInputService(Session session) {
    contactDataService.setSession(session);
  }

  @Override
  public void processValueArray(String[] values) {
    if (values.length != 4) {
      return;
    }

    Long contactId = Long.parseLong(values[0]);
    Long personId = Long.parseLong(values[3]);

    ContactDto contactDto = new ContactDto(contactId, values[1],
                                           values[2], personId);

    contactDataService.save(contactDto);
  }

  @Override
  public void clearDatabaseTable() {
    List<ContactDto> contactDtos = contactDataService.getAll();

    for (ContactDto contactDto : contactDtos) {
      contactDataService.delete(contactDto);
    }
  }

}