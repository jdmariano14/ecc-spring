package com.exist.ecc.person.core.dao.impl;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.model.Person;

public class PersonHibernateDao extends AbstractDao<Person,Long> implements PersonDao {

  public PersonHibernateDao() {
    super();
  }

  public PersonHibernateDao(Session session) {
    super(session);
  }
  
}
