package com.exist.ecc.person.core.service.data.impl;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.api.CriteriaDao;
import com.exist.ecc.person.core.service.data.api.DataService;

public abstract class AbstractDataService<T, I> implements DataService<T, I> {
/*
  public <R> void updateSet(Set<R> oldSet,
                            Set<I> newIdSet,
                            Function<R, I> idMethod,
                            String idString,
                            CriteriaDao<T, I> dao)
  {
    Set<I> oldIdSet = oldSet.stream()
                            .map(idMethod)
                            .collect(Collectors.toSet());

    Set<I> addedIdSet = newIdSet.removeAll(oldSet);
    Set<I> removedIdSet = oldSet.removeAll(newIdSet);

    Set<R> addedSet =
      dao.query(c -> c.add(Restrictions.in(idString, addedIdSet)));

    Set<R> removedSet =
      dao.query(c -> c.add(Restrictions.in(idString, removedIdSet)));

    for (R added : addedSet) {
      oldSet.add(added);
    }

    for (R removed : removedSet) {
      oldSet.remove(removed);
    }

  }
*/
}