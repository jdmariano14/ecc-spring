package com.exist.ecc.person.core.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.util.SessionUtil;

import com.exist.ecc.person.core.dao.api.Dao;

public class Transactions {
  
  public static void conduct(Dao dao, Runnable action) {
    Session session = null;
    Transaction transaction = null;

    try {
      session = SessionUtil.getSessionFactory().openSession();
      transaction = session.beginTransaction();
      dao.setSession(session);
      action.run();
      dao.flush();
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      System.out.println(e.getMessage());
    } finally {
      session.close();
    }
  }

}