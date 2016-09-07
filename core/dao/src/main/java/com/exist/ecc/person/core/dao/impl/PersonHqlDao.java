package com.exist.ecc.person.core.dao.impl;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import com.exist.ecc.person.core.model.Person;

import com.exist.ecc.person.core.dao.api.PersonDao;

public class PersonHqlDao extends HqlDaoImpl<Person, Long> 
  implements PersonDao 
{

  public List<Person> getAllById() {
    throw new UnsupportedOperationException();
  }

  public List<Person> queryLastName(String min, String max, String like, 
                                    boolean desc)
  {
    throw new UnsupportedOperationException();
  }

  public List<Person> queryDateHired(Date min, Date max, boolean desc) {
    throw new UnsupportedOperationException();
  }

  public List<Person> queryGwa(BigDecimal min, BigDecimal max, boolean desc) {
    throw new UnsupportedOperationException();
  }

}
