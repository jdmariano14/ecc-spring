package com.exist.ecc.person.core.service.data.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.api.CriteriaDao;
import com.exist.ecc.person.core.dao.impl.CriteriaDaoImpl;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

public class PersonDataService extends AbstractDataService<PersonDto, Long> {

  // @Autowired
  private CriteriaDao<Person, Long> personDao = new CriteriaDaoImpl();
  private CriteriaDao<Contact, Long> contactDao = new CriteriaDaoImpl();
  private CriteriaDao<Role, Long> roleDao = new CriteriaDaoImpl();

  @Override
  public PersonDto get(Long id) {
    return personDao.get(id).getDto();
  }
  
  @Override
  public List<PersonDto> getAll() {
    return personDao.query(c -> c.addOrder(Order.asc("personId")))
                  .stream()
                  .map(r -> r.getDto())
                  .collect(Collectors.toList());
  }

  @Override
  public void save(PersonDto dto) {
    Person person = personDao.get(dto.getPersonId());

    updateContacts(person, dto.getContactIds());
    updateRoles(person, dto.getRoleIds());

    person.readDto(dto);
    personDao.save(person);
  }
  
  @Override
  public void delete(PersonDto dto) {
    Person person = personDao.get(dto.getPersonId());

    person.getRoles().forEach(r -> r.getPersons().remove(person));
    person.getRoles().clear();
    
    personDao.delete(person);
  }

  private void updateContacts(Person person, Set<Long> newContactIds) {
    Set<Long> oldContactIds = person.getContacts().stream()
                                    .map(c -> c.getContactId())
                                    .collect(Collectors.toSet());

    Set<Long> addedContactIds = new HashSet(newContactIds);
    addedContactIds.removeAll(oldContactIds);

    Set<Long> removedContactIds = new HashSet(oldContactIds);
    removedContactIds.removeAll(newContactIds);

    List<Contact> addedContacts = contactDao.query(c -> 
      c.add(Restrictions.in("contactId", addedContactIds)));
    List<Contact> removedContacts = contactDao.query(c -> 
      c.add(Restrictions.in("contactId", removedContactIds)));

    person.getContacts().addAll(addedContacts);
    person.getContacts().removeAll(removedContacts);
  }

  private void updateRoles(Person person, Set<Long> newRoleIds) {
    Set<Long> oldRoleIds = person.getRoles().stream()
                                 .map(r -> r.getRoleId())
                                 .collect(Collectors.toSet());

    Set<Long> addedRoleIds = new HashSet(newRoleIds);
    addedRoleIds.removeAll(oldRoleIds);

    Set<Long> removedRoleIds = new HashSet(oldRoleIds);
    removedRoleIds.removeAll(newRoleIds);

    List<Role> addedRoles = roleDao.query(c -> 
      c.add(Restrictions.in("roleId", addedRoleIds)));
    List<Role> removedRoles = roleDao.query(c -> 
      c.add(Restrictions.in("roleId", removedRoleIds)));

    person.getRoles().addAll(addedRoles);
    person.getRoles().removeAll(removedRoles);
  }
}