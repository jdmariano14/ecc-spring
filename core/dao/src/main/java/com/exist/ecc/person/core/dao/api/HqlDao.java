package com.exist.ecc.person.core.dao.api;

import java.util.List;

public interface HqlDao<T> {

  public abstract List<T> query(String hql);
  
}
