package com.exist.ecc.person.core.service.validation;

import java.util.List;
import java.util.ArrayList;

public class RoleValidator {

  public static List<String> validateName(String name) {
    List<String> errors = new ArrayList();

    errors.addAll(Validations.validatePresence("name", name));

    return errors;
  }

}