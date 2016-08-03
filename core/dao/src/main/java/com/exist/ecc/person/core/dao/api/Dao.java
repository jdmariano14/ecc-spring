package com.exist.ecc.person.core.dao.api;

import java.util.List;

import org.hibernate.Session;

public interface Dao<T, I> {

  public abstract T findById(I id);

  public default T get(I id) {
    return findById(id);
  }
  
  public abstract List<T> findAll();
  
  public abstract T save(T entity);
  
  public abstract void delete(T entity);
  
  public abstract void flush();
  
  public abstract void clear();

  public abstract void setSession(Session session);
}
