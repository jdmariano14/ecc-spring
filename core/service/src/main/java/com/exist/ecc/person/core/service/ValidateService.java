package com.exist.ecc.person.core.service;

import java.util.Set;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidateService {

  private static final ValidatorFactory factory;
  private static final Validator validator;

  static {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }
  
}