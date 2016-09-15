package com.exist.ecc.person.core.service.data.api;

import java.util.List;

public interface DataService<T, I> {
  
  public abstract T get(I id);
  
  public abstract List<T> getAll();

  public abstract void save(T dto);
  
  public abstract void delete(T dto);

}