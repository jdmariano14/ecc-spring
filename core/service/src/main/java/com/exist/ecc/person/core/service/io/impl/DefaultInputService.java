package com.exist.ecc.person.core.service.io.impl;

import java.util.Set;
import java.util.Scanner;
import java.util.function.Function;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.exist.ecc.person.util.ValidateUtil;

import com.exist.ecc.person.core.service.io.api.InputService;
import com.exist.ecc.person.core.service.io.api.InputExtractor;
import com.exist.ecc.person.core.service.io.api.InputExceptionHandler;

public class DefaultInputService implements InputService {

  private InputExtractor extractor;
  private InputExceptionHandler exceptionHandler;

  public DefaultInputService() {

  }

  public DefaultInputService(InputExtractor extractor, InputExceptionHandler exceptionHandler) {
    this();
    setExtractor(extractor);
    setExcpetionHandler(exceptionHandler);
  }

  public void setExtractor(InputExtractor extractor) {
    this.extractor = extractor;
  }

  public void setExcpetionHandler(InputExceptionHandler exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public <R> R getInput(String promptMsg, Function<String, R> parse) {
    return exceptionHandler.handle(() -> {
      return parse.apply(extractor.extract(promptMsg));
    });
  }

  @Override
  public <T, R> R getValidInput(String promptMsg, Class<T> beanType, 
    String propertyName, Function<String, R> parse) 
  {
    return exceptionHandler.handle(() -> {
      R value = parse.apply(extractor.extract(promptMsg));
      Validator validator = ValidateUtil.getValidatorFactory().getValidator();
      
      Set<ConstraintViolation<T>> constraintViolations = 
        validator.validateValue(beanType, propertyName, value);

      if (!constraintViolations.isEmpty()) {
        throw new IllegalArgumentException("invalid value");
      }

      return value;
    });
  }
}