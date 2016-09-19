package com.exist.ecc.person.core.dao.api;

import java.util.List;

import java.util.function.Supplier;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface Dao<T, I> {

  public abstract SessionFactory getSessionFactory();

  public abstract void setSessionFactory(SessionFactory sessionFactory);

  public abstract Session getSession();

  public abstract void setSession(Session session);
  
  public abstract T get(I id);
  
  public abstract List<T> getAll();

  public abstract void save(T entity);
  
  public abstract void delete(T entity);
  
  public abstract void flush();
  
  public abstract void clear();

  public abstract void conduct(Runnable action);

  public abstract <R> R extract(Supplier<R> supplier);

}
