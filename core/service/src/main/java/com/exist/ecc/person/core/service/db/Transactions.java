package com.exist.ecc.person.core.service.db;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.util.SessionUtil;

import com.exist.ecc.person.core.dao.api.Dao;

public class Transactions {
  
  public static void conduct(Runnable action, Dao... daos) {
    Session session = null;
    Transaction transaction = null;

    try {
      session = SessionUtil.getSessionFactory().openSession();
      transaction = session.beginTransaction();

      for (Dao dao : daos) {
        dao.setSession(session);  
      }
      
      action.run();

      for (Dao dao : daos) {
        dao.flush();
      }

      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      System.out.println(e.getMessage());
    } finally {
      session.close();
    }
  }

}