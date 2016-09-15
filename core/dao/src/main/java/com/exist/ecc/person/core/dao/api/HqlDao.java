package com.exist.ecc.person.core.dao.api;

import java.util.List;

public interface HqlDao<T, I> extends Dao<T, I> {

  public abstract List<T> query(String hql);
  
}
