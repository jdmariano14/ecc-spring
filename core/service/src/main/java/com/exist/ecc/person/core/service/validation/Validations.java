package com.exist.ecc.person.core.service.validation;

import java.util.Set;
import java.util.function.Consumer;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.exist.ecc.person.util.ValidateUtil;

public class Validations {

  public static <T, E> Consumer<T> getValidation(Class<E> beanType, 
    String propertyName) 
  {
    return (T value) -> {
      Validator validator = ValidateUtil.getValidatorFactory().getValidator();
      
      Set<ConstraintViolation<E>> constraintViolations = 
        validator.validateValue(beanType, propertyName, value);

      if (!constraintViolations.isEmpty()) {
        throw new IllegalArgumentException("Something bad happened");
      }
    };
  }
}