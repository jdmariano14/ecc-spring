package com.exist.ecc.person.core.service.data.impl;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.exist.ecc.person.core.service.data.api.DataService;

public abstract class AbstractDataService<T, I> implements DataService<T, I> {
/*
  public void updateSet(Set<T> oldSet,
                        Set<I> newSet,
                        Function<T, I> idMethod,
                        Dao<T, I> dao) 
  {
    Set<I> oldIdSet = oldSet.stream()
                             .map(idMethod)
                             .collect(Collectors.toSet());

    Set<I> addedIdSet = newSet.removeAll(oldSet);
    Set<I> removedIdSet = oldSet.removeAll(newSet);

    //dao.get(addedIdSet)

  }
*/
}