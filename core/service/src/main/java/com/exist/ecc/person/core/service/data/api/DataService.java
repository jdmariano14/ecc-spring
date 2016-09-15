package com.exist.ecc.person.core.service.data.api;

import java.util.List;

import org.hibernate.Session;

public interface DataService<T, I> {

  public abstract Session getSession();

  public abstract void setSession(Session session);
  
  public abstract T get(I id);
  
  public abstract List<T> getAll();

  public abstract void save(T dto);
  
  public abstract void delete(T dto);

}