package com.exist.ecc.person.core.service.data.impl;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.api.CriteriaDao;
import com.exist.ecc.person.core.service.data.api.DataService;

public abstract class AbstractDataService<T, I> implements DataService<T, I> {

  private Session session;

  @Override
  public Session getSession() {
    return session;
  }

  @Override
  public void setSession(Session session) {
    this.session = session;
  }

}