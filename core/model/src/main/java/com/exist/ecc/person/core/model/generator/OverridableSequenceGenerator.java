package com.exist.ecc.person.core.model.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.SequenceGenerator;

import com.exist.ecc.person.core.model.OverridableIdModel;

public class OverridableSequenceGenerator extends SequenceGenerator {

  @Override
  public Serializable generate(SessionImplementor session, Object object)
    throws HibernateException
  {
    Serializable returnId;


    if (object instanceof OverridableIdModel
        && ((OverridableIdModel) object).getOverrideId() != null )
    {
      returnId = ((OverridableIdModel) object).getOverrideId();
    } else {
      returnId = super.generate(session, object);
    }

    return returnId;
  }

}