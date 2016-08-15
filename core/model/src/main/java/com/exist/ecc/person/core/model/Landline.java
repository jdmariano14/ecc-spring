package com.exist.ecc.person.core.model;

<<<<<<< Updated upstream
public class Landline extends ContactDetail {
  
  protected boolean isValidDetail(String detail) {
    throw new UnsupportedOperationException();
  }
=======
import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("LANDLINE")
public class Landline extends Contact {
>>>>>>> Stashed changes
  
}