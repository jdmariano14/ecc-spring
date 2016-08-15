package com.exist.ecc.person.core.dao.impl;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.api.ContactDao;
import com.exist.ecc.person.core.model.Contact;

public class ContactHibernateDao extends AbstractDao<Contact,Long>
  implements ContactDao
{
  
  public ContactHibernateDao() {
    super();
  }

  public ContactHibernateDao(Session session) {
    super(session);
  }
  
}
