package com.exist.ecc.person.core.dao.impl;

import java.io.Serializable;

import java.util.List;
import java.util.function.UnaryOperator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.exist.ecc.person.core.dao.api.CriteriaDao;

public class CriteriaDaoImpl<T, I extends Serializable> 
  extends AbstractDao<T, I> implements CriteriaDao<T>
{

  @Override
  public List<T> query(UnaryOperator<Criteria> crit) {
    Criteria criteria = getSession().createCriteria(getPersistentClass());
    
    return (List<T>) crit.apply(criteria).list(); 
  }

  @Override
  public List<T> getAll() {
    return query(c -> c);
  }

  public UnaryOperator<Criteria> order(String property, boolean desc) {
    return c -> {
      c.addOrder(desc ? Order.desc(property)
                      : Order.asc(property));

      return c;
    };
  }

}