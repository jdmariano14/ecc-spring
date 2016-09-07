package com.exist.ecc.person.core.dao.impl;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.api.PersonDao;

import com.exist.ecc.person.core.model.Person;

public class PersonCriteriaDao extends CriteriaDaoImpl<Person, Long>
  implements PersonDao
{
  
  public List<Person> getAllById() {
    return query(c -> c.addOrder(Order.asc("personId")));
  }

  public List<Person> queryLastName(String min, String max, String like, 
                                    boolean desc)
  {
    return query(c -> {
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
    });
  }

  public List<Person> queryDateHired(Date min, Date max, boolean desc) {
    return query(c -> {
      if (min != null) {
        c.add(Restrictions.ge("dateHired", min));
      }

      if (max != null) {
        c.add(Restrictions.le("dateHired", max));
      }

      return order("dateHired", desc).apply(c);
    });
  }

  public List<Person> queryGwa(BigDecimal min, BigDecimal max, boolean desc) {
    return query(c -> {
      if (min != null) {
        c.add(Restrictions.ge("gwa", min));
      }

      if (max != null) {
        c.add(Restrictions.le("gwa", max));
      }

      return order("gwa", desc).apply(c);
    });
  }
}