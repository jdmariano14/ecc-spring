package com.exist.ecc.person.app.impl;

import java.util.function.BiFunction;

import org.hibernate.criterion.Order;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.dao.impl.RoleHibernateDao;

import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.db.Transactions;
import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.impl.RoleWizard;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.impl.RoleFormatter;

public class RoleManager extends AbstractEntityManager {

  private final RoleDao roleDao;

  public RoleManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    super(reader, writer, handler);
    roleDao = new RoleHibernateDao();
  }

  public void create() {
    final Role role = new Role();
        
    getWriter().write("");
    setRoleFields(role);

    Transactions.conduct(() -> { 
      roleDao.save(role);
    }, roleDao);
  }

  public void list() {
    getWriter().write("");

    Transactions.conduct(() -> { 
      OutputFormatter<Role> formatter = new RoleFormatter();

      roleDao.query(c -> c.addOrder(Order.asc("roleId")))
             .forEach(r -> getWriter().write(formatter.format(r)));
    }, roleDao);
  }

  public void update() {
    long id = getId("role");

    Transactions.conduct(() -> {
      final Role role = roleDao.get(id);
          
      getWriter().write("");
      setRoleFields(role);
      roleDao.save(role);
    }, roleDao);
  }

  public void delete() {
    long id = getId("role");

    Transactions.conduct(() -> { 
      final Role role = roleDao.get(id);
          
      getWriter().write("");

      if (getDeleteConfirmation("role", role.toString())) {
        roleDao.delete(role);
      }
    }, roleDao);
  }

  private void setRoleFields(Role role) {    
    RoleWizard roleWizard = new RoleWizard(getReader(), getHandler());

    BiFunction<String, Object, String> defaultFormat = (s, o) -> {
      return o == null
             ? String.format("%s: ", AppUtil.defaultTransform(s))
             : String.format("%s (%s): ", AppUtil.defaultTransform(s), o);
    };

    roleWizard.setDefaultFormat(defaultFormat);
    roleWizard.setProperties(role);
  }

}