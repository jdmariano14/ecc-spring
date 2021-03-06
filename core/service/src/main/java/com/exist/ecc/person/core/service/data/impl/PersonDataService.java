package com.exist.ecc.person.core.service.data.impl;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
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

  private PersonCriteriaDao personDao;
  private ContactCriteriaDao contactDao;
  private RoleCriteriaDao roleDao;

  public void setPersonDao(PersonCriteriaDao personDao) {
    this.personDao = personDao;
  }

  public void setContactDao(ContactCriteriaDao contactDao) {
    this.contactDao = contactDao;
  }

  public void setRoleDao(RoleCriteriaDao roleDao) {
    this.roleDao = roleDao;
  }

  @Override
  public PersonDto get(Long id) {
    return personDao.get(id).getDto();
  }
  
  @Override
  public List<PersonDto> getAll() {
    return getDtos(personDao.query(c -> c.addOrder(Order.asc("personId"))));
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

  public List<PersonDto> queryLastName(String min, 
                                       String max, 
                                       String like, 
                                       boolean desc)
  {
    return getDtos(personDao.query(c -> {
      if (!min.isEmpty()) {
        c.add(Restrictions.ge("name.lastName", min).ignoreCase());
      }

      if (!max.isEmpty()) {
        c.add(Restrictions.le("name.lastName", max).ignoreCase());
      }

      if (!like.isEmpty()) {
        c.add(Restrictions.like("name.lastName", like).ignoreCase());
      }

      return order("name.lastName", desc).apply(c);
    }));
  }

  public List<PersonDto> queryDateHired(Date min, Date max, boolean desc) {
    return getDtos(personDao.query(c -> {
      if (min != null) {
        c.add(Restrictions.ge("dateHired", min));
      }

      if (max != null) {
        c.add(Restrictions.le("dateHired", max));
      }

      return order("dateHired", desc).apply(c);
    }));
  }

  public List<PersonDto> queryGwa(BigDecimal min, BigDecimal max, boolean desc) {
    return getDtos(personDao.query(c -> {
      if (min != null) {
        c.add(Restrictions.ge("gwa", min));
      }

      if (max != null) {
        c.add(Restrictions.le("gwa", max));
      }

      return order("gwa", desc).apply(c);
    }));
  }

  private List<PersonDto> getDtos(List<Person> persons) {
    return persons.stream()
                  .map(r -> r.getDto())
                  .collect(Collectors.toList());
  }

  private UnaryOperator<Criteria> order(String property, boolean desc) {
    return c -> {
      c.addOrder(desc ? Order.desc(property)
                      : Order.asc(property));

      return c;
    };
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

    if (newContactIds != null) {
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

    if (newRoleIds != null) {
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
}