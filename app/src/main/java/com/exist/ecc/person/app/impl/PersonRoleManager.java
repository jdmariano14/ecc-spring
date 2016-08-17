package com.exist.ecc.person.app.impl;
import java.util.Collection;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.BasicPersonFormatter;
import com.exist.ecc.person.core.service.output.impl.ComposedRoleFormatter;
import com.exist.ecc.person.core.service.output.impl.RoleFormatter;

import com.exist.ecc.person.core.service.db.Transactions;

public class PersonRoleManager extends AbstractEntityManager {

  private final PersonCriteriaDao personDao;
  private final RoleCriteriaDao roleDao;

  public PersonRoleManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    super(reader, writer, handler);
    personDao = new PersonCriteriaDao();
    roleDao = new RoleCriteriaDao();
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
      
      getWriter().write("");

      if (roles.isEmpty()) {
        getWriter().write(personFormatter.format(person) + " has no roles");
      } else {
        OutputFormatter<Role> formatter = new ComposedRoleFormatter();
        String entityString;

        getWriter().write(personFormatter.format(person) + "'s roles:");
        roles.forEach(r -> getWriter().write(roleFormatter.format(r)));

        getWriter().write("");

        roleId = getId("role");
        role = roleDao.get(roleId);

        entityString =
          new StringBuilder()
          .append(personFormatter.format(person))
          .append(": ")
          .append(formatter.format(role))
          .toString();

        if (getDeleteConfirmation("person role", entityString)) {
          person.getRoles().remove(role);
          personDao.save(person);
        }
      }
    }, personDao, roleDao);
  }

}