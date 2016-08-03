package com.exist.ecc.person.core.dao.impl;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.api.ContactDetailDao;
import com.exist.ecc.person.core.model.ContactDetail;

public class ContactDetailHibernateDao extends AbstractDao<ContactDetail,Long> implements ContactDetailDao {
  
  public ContactDetailHibernateDao() {
    super();
  }

  public ContactDetailHibernateDao(Session session) {
    super(session);
  }
  
}
