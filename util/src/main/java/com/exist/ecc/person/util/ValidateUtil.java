package com.exist.ecc.person.util;

import java.util.Set;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidateUtil {

  private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

  public static ValidatorFactory getValidatorFactory() {
    return validatorFactory;
  } 
  
}