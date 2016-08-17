package com.exist.ecc.person.core.dao.impl;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;
import java.util.function.UnaryOperator;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.exist.ecc.person.core.dao.api.Dao;

import com.exist.ecc.person.util.SessionUtil;

public abstract class AbstractDao<T, I extends Serializable> 
  implements Dao<T, I> 
{

  private Class<T> persistentClass;
  private Session session;
  
  public AbstractDao() {
    persistentClass = determinePersistentClass();
  }
  
  @Override
  public Session getSession() {
    if (session == null) {
      session = SessionUtil.getSessionFactory().getCurrentSession();
    }
    return session;
  }

  @Override
  public void setSession(Session newSession) {
    session = newSession;
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
  
  @Override
  public void flush() {
    getSession().flush();
  }

  @Override
  public void clear() {
    getSession().clear();
  }

  private Class<T> determinePersistentClass() {
    ParameterizedType parametrizedType =
      (ParameterizedType) getClass().getGenericSuperclass();

    Type type = parametrizedType.getActualTypeArguments()[0];

    return (Class<T>) type;
  }

}
