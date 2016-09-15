package com.exist.ecc.person.core.service.data.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.criterion.Order;

import com.exist.ecc.person.core.dao.api.CriteriaDao;
import com.exist.ecc.person.core.dao.impl.CriteriaDaoImpl;
import com.exist.ecc.person.core.dto.ContactDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Person;

public class ContactDataService 
  extends AbstractDataService<ContactDto, Long> 
{

  // @Autowired
  private CriteriaDao<Person, Long> personDao = new CriteriaDaoImpl();
  private CriteriaDao<Contact, Long> contactDao = new CriteriaDaoImpl();

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
    Contact contact = contactDao.get(dto.getContactId());
    
    contact.readDto(dto);
    contact.setPerson(personDao.get(dto.getPersonId()));

    contactDao.save(contact);
  }
  
  @Override
  public void delete(ContactDto dto) {
    Contact contact = contactDao.get(dto.getContactId());
    
    contactDao.delete(contact);
  }

}