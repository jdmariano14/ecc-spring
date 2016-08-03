package com.exist.ecc.person.core.service.validation;

import java.util.List;

public class Validations {

  public static void validateStringPresence(String fieldName, String input, List errors) {
    if (input.isEmpty()) {
      StringBuilder errMsg = new StringBuilder(fieldName);
      errMsg.append(" cannot be blank");

      errors.add(errMsg.toString());
    }
  }

}