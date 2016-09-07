package com.exist.ecc.person.core.dao.impl;

import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.api.RoleDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

public class RoleCriteriaDao extends CriteriaDaoImpl<Role, Long> 
  implements RoleDao
{

  public List<Role> getAllById() {
    return query(c -> c.addOrder(Order.asc("roleId")));
  }

  public List<Role> getAllGrantable(Person person) {
    Set<Long> roleIdSubquery = 
      person.getRoles().stream()
            .map(r -> r.getRoleId())
            .collect(Collectors.toSet());

    return query(c -> {
      if (!roleIdSubquery.isEmpty()) {
        c.add(Restrictions.not(Restrictions.in("roleId", roleIdSubquery)));        
      }

      return c.addOrder(Order.asc("roleId"));
    });
  }

}
