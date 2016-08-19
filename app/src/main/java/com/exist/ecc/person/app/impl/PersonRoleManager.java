package com.exist.ecc.person.app.impl;

import java.util.Collection;

import org.hibernate.Session;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
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

    Session session = Sessions.getSession();

    try {
      final Person person =
        Transactions.get(() -> personDao.get(personId), session, personDao);

      final Role role =
        Transactions.get(() -> roleDao.get(roleId), session, roleDao);

      person.getRoles().add(role);

      Transactions.conduct(() -> personDao.save(person), session, personDao);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }

  public void list() {
    throw new UnsupportedOperationException();
  }

  public void update() {
    throw new UnsupportedOperationException();
  }

  public void delete() {
    final Collection<Role> roles;

    Session session = Sessions.getSession();

    try {
      OutputFormatter<Person> personFormatter = new BasicPersonFormatter();
      OutputFormatter<Role> roleFormatter = new RoleFormatter();
      long personId = getId("person");
      long roleId;

      final Person person =
        Transactions.get(() -> personDao.get(personId), session, personDao);

      roles = person.getRoles();

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
        
        final Role role =
          Transactions.get(() -> roleDao.get(roleId), session, roleDao);

        entityString =
          new StringBuilder()
          .append(personFormatter.format(person))
          .append(": ")
          .append(formatter.format(role))
          .toString();

        if (getDeleteConfirmation("person role", entityString)) {
          person.getRoles().remove(role);

          Transactions.conduct(
            () -> personDao.save(person), session, personDao);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }

}