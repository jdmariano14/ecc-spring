package com.exist.ecc.person.app.impl;

import java.util.Collection;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.dao.impl.PersonHibernateDao;
import com.exist.ecc.person.core.dao.impl.RoleHibernateDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.BasicPersonFormatter;
import com.exist.ecc.person.core.service.output.impl.RoleFormatter;

import com.exist.ecc.person.core.service.db.Transactions;

public class PersonRoleManager extends AbstractEntityManager {

  private final PersonDao personDao;
  private final RoleDao roleDao;

  public PersonRoleManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    super(reader, writer, handler);
    personDao = new PersonHibernateDao();
    roleDao = new RoleHibernateDao();
  }

  public void create() {
    long personId = getId("person");
    long roleId = getId("role");

    Transactions.conduct(() -> { 
      final Person person = personDao.get(personId);
      final Role role = roleDao.get(roleId);
      
      person.getRoles().add(role);
      personDao.save(person);
    }, personDao, roleDao);
  }

  public void list() {
    throw new UnsupportedOperationException();
  }

  public void update() {
    throw new UnsupportedOperationException();
  }

  public void delete() {
    long personId = getId("person");

    Transactions.conduct(() -> {
      final Person person = personDao.get(personId);
      final Collection<Role> roles = person.getRoles();
      final Role role;
      long roleId;
      OutputFormatter<Person> personFormatter = new BasicPersonFormatter();
      OutputFormatter<Role> roleFormatter = new RoleFormatter();

      if (roles.isEmpty()) {
        getWriter().write(personFormatter.format(person) + " has no roles");
      } else {
        getWriter().write(personFormatter.format(person) + "'s roles:");
        roles.forEach(r -> getWriter().write(roleFormatter.format(r)));

        roleId = getId("role");
        role = roleDao.get(roleId);

        if (getDeleteConfirmation("person role", role.getName())) {
          roles.remove(role);
          personDao.save(person);
        }
      }
    }, personDao, roleDao);
  }

}