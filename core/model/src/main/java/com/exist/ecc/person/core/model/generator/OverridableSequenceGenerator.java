package com.exist.ecc.person.core.model.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.SequenceGenerator;

public class OverridableSequenceGenerator extends SequenceGenerator {

  @Override
  public Serializable generate(SessionImplementor session, Object object)
    throws HibernateException
  {
    Serializable returnId;

    Serializable currentId = session.getEntityPersister(null, object)
                                    .getClassMetadata()
                                    .getIdentifier(object, session);

    if (currentId == null || (Long) currentId <= 0 ) {
      returnId = super.generate(session, object);
    } else {
      returnId = currentId;
    }

    return returnId;
  }

}