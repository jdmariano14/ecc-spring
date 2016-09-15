package com.exist.ecc.person.core.dao.api;

import java.util.List;

import java.util.function.UnaryOperator;

import org.hibernate.Session;
import org.hibernate.Criteria;

public interface CriteriaDao<T, I> extends Dao<T, I> {

  public abstract List<T> query(UnaryOperator<Criteria> crit);

}
