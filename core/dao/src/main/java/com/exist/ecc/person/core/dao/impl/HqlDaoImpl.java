package com.exist.ecc.person.core.dao.impl;

import java.io.Serializable;

import java.util.List;

import org.hibernate.Query;

import com.exist.ecc.person.core.dao.api.HqlDao;

public class HqlDaoImpl<T, I extends Serializable> 
  extends AbstractDao<T, I> implements HqlDao<T>
{

  @Override
  public List<T> query(String hql) {
    String fullHql =
      new StringBuilder()
      .append("FROM ")
      .append(getPersistentClass().getSimpleName())
      .append(" ")
      .append(hql)
      .toString();

    Query query = getSession().createQuery(fullHql);
    
    return (List<T>) query.list(); 
  }

  @Override
  public List<T> getAll() {
    return query("");
  }

}
