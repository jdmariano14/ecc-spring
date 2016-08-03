package com.exist.ecc.person.core.dao.api;

import java.util.List;

import org.hibernate.Session;

public interface Dao<T, I> {

  public abstract T get(I id);
  
  public default T find(I id) {
    return get(id);
  }
  
  public abstract List<T> getAll();
  
  public abstract T save(T entity);
  
  public abstract void delete(T entity);
  
  public abstract void flush();
  
  public abstract void clear();

  public abstract void setSession(Session session);
}
