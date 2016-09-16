package com.exist.ecc.person.core.service.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.impl.ContactCriteriaDao;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;
import com.exist.ecc.person.core.dto.ContactDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Email;
import com.exist.ecc.person.core.model.Landline;
import com.exist.ecc.person.core.model.Mobile;
import com.exist.ecc.person.core.model.Person;

public class ContactDataService 
  extends AbstractDataService<ContactDto, Long> 
{
  
  private ContactCriteriaDao contactDao = new ContactCriteriaDao();
  private PersonCriteriaDao personDao = new PersonCriteriaDao();

  @Override
  public void setSession(Session session) {
    super.setSession(session);
    contactDao.setSession(session);
    personDao.setSession(session);
  }

  @Override
  public ContactDto get(Long id) {
    return contactDao.get(id).getDto();
  }
  
  @Override
  public List<ContactDto> getAll() {
    return contactDao.query(c -> c.addOrder(Order.asc("contactId")))
                     .stream()
                     .map(r -> r.getDto())
                     .collect(Collectors.toList());
  }

  @Override
  public void save(ContactDto dto) {
    Contact contact = null;

    try {
      contact = contactDao.get(dto.getContactId());

      contact.readDto(dto);
      contact.setPerson(personDao.get(dto.getPersonId()));

      contactDao.save(contact);
    } catch (Exception e) {
      switch(dto.getContactType().toUpperCase()) {
        case "EMAIL":
          contact = new Email();
          break;
        case "LANDLINE":
          contact = new Landline();
          break;
        case "MOBILE":
          contact = new Mobile();
          break;
        default:
          throw e;
      }

      contact.readDto(dto);
      contact.setPerson(personDao.get(dto.getPersonId()));

      contactDao.save(contact);
    }
  }
  
  @Override
  public void delete(ContactDto dto) {
    Contact contact = contactDao.get(dto.getContactId());
    
    contactDao.delete(contact);
  }

  public List<ContactDto> getFromPerson(PersonDto personDto) {
    List<ContactDto> contactDtos;
    Set<Long> contactIds = personDto.getContactIds();

    if (!contactIds.isEmpty()) {
      contactDtos = 
        contactDao.query(c -> c.add(Restrictions.in("contactId", contactIds)))
                  .stream()
                  .map(r -> r.getDto())
                  .collect(Collectors.toList());
    } else {
      contactDtos = new ArrayList(0);
    }

    return contactDtos;
  }

}