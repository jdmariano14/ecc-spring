package com.exist.ecc.person.core.dao;

import java.util.Collection;
import java.util.function.Supplier;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.core.dao.api.Dao;

public class Transactions {
  
  public static void conduct(Session session, Dao dao, Runnable action) {
    Transaction transaction = null;

    try {
      dao.setSession(session);
      transaction = session.beginTransaction();
      action.run();
      dao.flush();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }

      throw new RuntimeException(e);
    }
  }

  public static <T> T get(Session session, Dao dao, Supplier<T> getter) {
    Transaction transaction = null;
    T result = null;

    try {
      dao.setSession(session);
      transaction = session.beginTransaction();
      result = getter.get();
      dao.flush();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }

      throw new RuntimeException(e);
    }

    return result;
  }

}