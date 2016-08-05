package com.exist.ecc.person.core.service.validation;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.exist.ecc.person.util.ValidateUtil;

public class Validations {

  public static <T, E> Consumer<T> get(Class<E> beanType, String propertyName) {
    return (T value) -> {
      Validator validator = ValidateUtil.getValidatorFactory().getValidator();
      
      Set<ConstraintViolation<E>> violations = 
        validator.validateValue(beanType, propertyName, value);

      if (!violations.isEmpty()) {
        StringBuilder exceptionMsg = new StringBuilder("Validation failed");
        exceptionMsg.append(" with the following violation(s): ");

        for (Iterator<ConstraintViolation<E>> i = violations.iterator(); i.hasNext(); ) {
          ConstraintViolation<E> violation = i.next();

          exceptionMsg.append(violation.getRootBeanClass().getSimpleName());
          exceptionMsg.append(" ");
          exceptionMsg.append(violation.getPropertyPath().toString());
          exceptionMsg.append(" ");
          exceptionMsg.append(violation.getMessage());

          if (i.hasNext()) {
            exceptionMsg.append(", ");
          }
        }

        throw new IllegalArgumentException(exceptionMsg.toString());
      }
    };
  }
}