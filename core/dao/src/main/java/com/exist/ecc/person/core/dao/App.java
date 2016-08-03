package com.exist.ecc.person.core.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.util.HibernateUtil;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.impl.PersonHibernateDao;

public class App {

  public static void main( String[] args ) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    PersonDao personDao = new PersonHibernateDao(session);

    try {
      Transaction transaction = session.beginTransaction();
      
      Person diene = new Person();
      Name name = new Name();
      Address address = new Address();

      diene.setBirthDate(new Date());
      diene.setGwa(new BigDecimal("1.77"));
      diene.setDateHired(new Date());
      diene.setEmployed(false);

      name.setFirstName("Diene");
      name.setTitle("Dr.");

      address.setStreetAddress("33 N, SF");
      address.setBarangay("DM");
      address.setMunicipality("QC");

      diene.setName(name);
      diene.setAddress(address);

      personDao.save(diene);
      
      transaction.commit();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      HibernateUtil.getSessionFactory().close();
    }
  }

}
