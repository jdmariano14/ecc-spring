package com.exist.ecc.person.core.dao.api;

import java.util.List;

import java.util.function.Supplier;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

public interface Dao<T, I> {

  public abstract SessionFactory getSessionFactory();

  public abstract void setSessionFactory(SessionFactory sessionFactory);
  
  public abstract T get(I id);
  
  public abstract List<T> getAll();

  public abstract void save(T entity);
  
  public abstract void delete(T entity);

}
