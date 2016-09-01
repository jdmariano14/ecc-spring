package com.exist.ecc.person.core.dao.impl;

import java.util.List;
import java.util.function.UnaryOperator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.exist.ecc.person.core.dao.api.RoleDao;

import com.exist.ecc.person.core.model.Role;

public class RoleCriteriaDao extends CriteriaDaoImpl<Role, Long> 
  implements RoleDao
{

  public List<Role> getAllById(boolean desc) {
    UnaryOperator<Criteria> query = c ->
      c.addOrder(desc ? Order.desc("roleId") : Order.asc("roleId"));
      
    return query(query);
  }

}
