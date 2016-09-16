package com.exist.ecc.person.core.service.data.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.impl.ContactCriteriaDao;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

public class PersonDataService extends AbstractDataService<PersonDto, Long> {

  private PersonCriteriaDao personDao = new PersonCriteriaDao();
  private ContactCriteriaDao contactDao = new ContactCriteriaDao();
  private RoleCriteriaDao roleDao = new RoleCriteriaDao();

  @Override
  public void setSession(Session session) {
    super.setSession(session);
    personDao.setSession(session);
    contactDao.setSession(session);
    roleDao.setSession(session);
  }

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
    Person person = null;

    try {
      person = personDao.get(dto.getPersonId());

      person.readDto(dto);
      updateContacts(person, dto.getContactIds());
      updateRoles(person, dto.getRoleIds());
      personDao.save(person);
    } catch (Exception e) {
      Name name = new Name();
      Address address = new Address();

      person = new Person();
      person.setName(name);
      person.setAddress(address);

      person.readDto(dto);
      updateContacts(person, dto.getContactIds());
      updateRoles(person, dto.getRoleIds());
      personDao.save(person);
    }
  }
  
  @Override
  public void delete(PersonDto dto) {
    Person person = personDao.get(dto.getPersonId());

    person.getRoles().forEach(r -> r.getPersons().remove(person));
    person.getRoles().clear();
    
    personDao.delete(person);
  }

  private void updateContacts(Person person, Set<Long> newContactIds) {
    Set<Long> oldContactIds; 

    try {
      oldContactIds = person.getContacts().stream()
                            .map(c -> c.getContactId())
                            .collect(Collectors.toSet());
    } catch (NullPointerException e) {
      oldContactIds = new HashSet();
    }

    Set<Long> addedContactIds = new HashSet(newContactIds);
    addedContactIds.removeAll(oldContactIds);

    Set<Long> removedContactIds = new HashSet(oldContactIds);
    removedContactIds.removeAll(newContactIds);

    if (!addedContactIds.isEmpty()) {
      List<Contact> addedContacts = contactDao.query(c -> 
        c.add(Restrictions.in("contactId", addedContactIds)));
      
      person.getContacts().addAll(addedContacts); 
    }

    if (!removedContactIds.isEmpty()) {
      List<Contact> removedContacts = contactDao.query(c -> 
        c.add(Restrictions.in("contactId", removedContactIds)));

      person.getContacts().removeAll(removedContacts);
    }
  }

  private void updateRoles(Person person, Set<Long> newRoleIds) {
    Set<Long> oldRoleIds; 

    try {
      oldRoleIds = person.getRoles().stream()
                         .map(r -> r.getRoleId())
                         .collect(Collectors.toSet());
    } catch (NullPointerException e) {
      oldRoleIds = new HashSet();
    }

    Set<Long> addedRoleIds = new HashSet(newRoleIds);
    addedRoleIds.removeAll(oldRoleIds);

    Set<Long> removedRoleIds = new HashSet(oldRoleIds);
    removedRoleIds.removeAll(newRoleIds);

    if (!addedRoleIds.isEmpty()) {
      List<Role> addedRoles = roleDao.query(c -> 
        c.add(Restrictions.in("roleId", addedRoleIds)));

      person.getRoles().addAll(addedRoles);
    }

    if (!removedRoleIds.isEmpty()) {
      List<Role> removedRoles = roleDao.query(c -> 
        c.add(Restrictions.in("roleId", removedRoleIds)));

      person.getRoles().removeAll(removedRoles);
    }
  }
}