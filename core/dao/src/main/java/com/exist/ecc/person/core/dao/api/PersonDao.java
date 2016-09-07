package com.exist.ecc.person.core.dao.api;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import com.exist.ecc.person.core.model.Person;

public interface PersonDao extends Dao<Person, Long> {

  public abstract List<Person> getAllById();

  public abstract List<Person> queryLastName(String min, String max,
                                             String like, boolean desc);

  public abstract List<Person> queryDateHired(Date min, Date max,
                                              boolean desc);

  public abstract List<Person> queryGwa(BigDecimal min, BigDecimal max,
                                        boolean desc);

  
}
