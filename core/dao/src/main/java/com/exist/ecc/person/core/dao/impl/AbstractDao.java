package com.exist.ecc.person.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import com.exist.ecc.person.util.HibernateUtil;
import com.exist.ecc.person.core.dao.api.Dao;

public class AbstractDao<T, I extends Serializable> implements Dao<T, I> {

  private Class<T> persistentClass;
  private Session session;
  
  public AbstractDao() {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  public AbstractDao(Session session) {
    this();
    setSession(session);
  }
  
  public void setSession(Session session) {
    this.session = session;
  }
  
  protected Session getSession() {
    if (this.session == null) {
      this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    return this.session;
  }
  
  public Class<T> getPersistentClass() {
    return persistentClass;
  }
  
  @Override
  public T get(I id) {
    return (T) getSession().load(this.getPersistentClass(), id);
  }

  @Override
  public List<T> getAll() {
    return this.findByCriteria();
  }

  protected List<T> findByCriteria(Criterion ... crit) {
    Criteria criteria = this.getSession().createCriteria(this.getPersistentClass());
    
    for (Criterion c : crit) {
      criteria.add(c);
    }

    return (List<T>) criteria.list(); 
  }
  
  @Override
  public T save(T entity) {
    this.getSession().saveOrUpdate(entity);
    return entity;
  }

  @Override
  public void delete(T entity) {
    this.getSession().delete(entity);
  }

  @Override
  public void flush() {
    this.getSession().flush();
  }

  @Override
  public void clear() {
    this.getSession().clear();
  }

}
