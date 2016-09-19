package com.exist.ecc.person.core.dao.impl;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.api.Dao;

public abstract class AbstractDao<T, I extends Serializable> 
  implements Dao<T, I> 
{

  private Class<T> persistentClass;
  private Session session;
  private SessionFactory sessionFactory;
  
  public AbstractDao() {
    persistentClass = determinePersistentClass();
  }

  @Override
  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Override
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  public Class<T> getPersistentClass() {
    return persistentClass;
  }

  @Override
  public T get(I id) {
    return (T) getSession().load(getPersistentClass(), id);
  }
  
  @Override
  public void save(T entity) {
    getSession().saveOrUpdate(entity);
  }

  @Override
  public void delete(T entity) {
    getSession().delete(entity);
  }
  
  private Class<T> determinePersistentClass() {
    ParameterizedType parametrizedType =
      (ParameterizedType) getClass().getGenericSuperclass();

    Type type = parametrizedType.getActualTypeArguments()[0];

    return (Class<T>) type;
  }

}
