package com.exist.ecc.person.app.impl;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.impl.RoleWizard;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.impl.ComposedRoleFormatter;
import com.exist.ecc.person.core.service.output.impl.RoleFormatter;

public class RoleManager extends AbstractEntityManager {

  private final RoleCriteriaDao roleDao;

  public RoleManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    super(reader, writer, handler);
    roleDao = new RoleCriteriaDao();
  }

  public void create() {
    Session session = Sessions.getSession();

    try {
      Role role = new Role();

      getWriter().write("");

      setRoleFields(role);

      Transactions.conduct(session, roleDao, () -> 
        roleDao.save(role));
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }

  public void list() {

    Session session = Sessions.getSession();

    try {
      OutputFormatter<Role> formatter = new RoleFormatter();

      Collection<Role> roles = Transactions.get(session, roleDao, () -> 
        roleDao.query(c -> c.addOrder(Order.asc("roleId"))));

      getWriter().write("");

      roles.forEach(r -> getWriter().write(formatter.format(r)));

    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }

  public void update() {
    Session session = Sessions.getSession();

    try {
      long id = getId("role");

      final Role role = Transactions.get(session, roleDao, () ->
        roleDao.get(id));

      getWriter().write("");

      setRoleFields(role);

      Transactions.conduct(session, roleDao, () -> 
        roleDao.save(role));
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }

  public void delete() {
    Session session = Sessions.getSession();

    try {
      OutputFormatter<Role> formatter = new ComposedRoleFormatter();
      long id = getId("role");
      String entityString;

      final Role role = Transactions.get(session, roleDao, () ->
        roleDao.get(id));

      entityString = formatter.format(role);

      if (getDeleteConfirmation("role", entityString)) {
        Transactions.conduct(session, roleDao, () -> 
          roleDao.delete(role));
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }

  private void setRoleFields(Role role) {    
    RoleWizard roleWizard = new RoleWizard(getReader(), getHandler());

    Function<String, String> defaultBlankFormat = 
      s -> String.format("%s: ", AppUtil.defaultTransform(s));

    BiFunction<String, Object, String> defaultFilledFormat = 
      (s, o) -> String.format("%s (%s): ", AppUtil.defaultTransform(s), o);

    roleWizard.setDefaultFormat(defaultBlankFormat, defaultFilledFormat);
    roleWizard.setProperties(role);
  }
}
