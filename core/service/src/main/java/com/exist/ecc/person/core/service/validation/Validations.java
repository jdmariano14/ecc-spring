package com.exist.ecc.person.core.service.validation;

import java.util.List;
import java.util.ArrayList;

public class Validations {

  public static List<String> validatePresence(String fieldName, String input) {
    List<String> errors = new ArrayList(1);
    
    if (input.isEmpty()) {
      StringBuilder errMsg = new StringBuilder(fieldName);
      errMsg.append(" cannot be blank");

      errors.add(errMsg.toString());
    }

    return errors;
  }

}