package com.exist.ecc.person.core.dao.impl;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.model.Role;

public class RoleHibernateDao extends AbstractDao<Role,Long> implements RoleDao {

  public RoleHibernateDao() {
    super();
  }

  public RoleHibernateDao(Session session) {
    super(session);
  }
  
}
