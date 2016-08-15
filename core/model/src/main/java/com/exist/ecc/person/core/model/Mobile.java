package com.exist.ecc.person.core.model;

<<<<<<< Updated upstream
public class Mobile extends ContactDetail {
  
  protected boolean isValidDetail(String detail) {
    throw new UnsupportedOperationException();
  }
=======
import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("MOBILE")
public class Mobile extends Contact {
>>>>>>> Stashed changes
  
}