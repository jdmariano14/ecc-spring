package com.exist.ecc.person.core.dao.api;

import java.util.List;

import java.util.function.UnaryOperator;

import org.hibernate.Session;
import org.hibernate.Criteria;

public interface Dao<T, I> {
  
  public abstract Session getSession();

  public abstract void setSession(Session session);
  
  public abstract T get(I id);
  
  public abstract List<T> getAll();

  public abstract void save(T entity);
  
  public abstract void delete(T entity);
  
  public abstract void flush();
  
  public abstract void clear();

}
