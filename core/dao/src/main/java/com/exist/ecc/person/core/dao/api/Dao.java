package com.exist.ecc.person.core.dao.api;

import java.util.List;

import java.util.function.UnaryOperator;

import org.hibernate.Session;
import org.hibernate.Criteria;

public interface Dao<T, I> {

  public abstract T get(I id);
  
  public default T find(I id) {
    return get(id);
  }

  public abstract List<T> query(UnaryOperator<Criteria> crit);
  
  public default List<T> getAll() {
    return query(c -> c);
  }

  public abstract T save(T entity);
  
  public abstract void delete(T entity);
  
  public abstract void flush();
  
  public abstract void clear();

  public abstract void setSession(Session session);
}
