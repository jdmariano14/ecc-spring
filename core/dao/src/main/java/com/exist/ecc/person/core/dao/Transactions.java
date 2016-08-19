package com.exist.ecc.person.core.dao;

import java.util.Collection;
import java.util.function.Supplier;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.core.dao.api.Dao;

public class Transactions {
  
  public static void conduct(Runnable action, Session session, Dao... daos) {
    Transaction transaction = null;

    try {
      for (Dao dao : daos) {
        dao.setSession(session);  
      }
      
      transaction = session.beginTransaction();
      
      action.run();

      for (Dao dao : daos) {
        dao.flush();
      }

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }

      throw new RuntimeException(e);
    }
  }

  public static <T> T get(Supplier<T> getter, Session session, Dao... daos) {
    Transaction transaction = null;
    T result = null;

    try {
      for (Dao dao : daos) {
        dao.setSession(session);  
      }
      
      transaction = session.beginTransaction();
      
      result = getter.get();

      for (Dao dao : daos) {
        dao.flush();
      }

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