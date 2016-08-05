package com.exist.ecc.person.core.service.io.impl;

import java.util.Set;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Consumer;

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
  public <R> R getInput(String promptMsg, Function<String, R> parse, R defaultValue) {
    return exceptionHandler.handle(() -> {
      String input = extractor.extract(promptMsg);
      R returnValue = defaultValue != null && input.isEmpty()
                      ? defaultValue 
                      : parse.apply(input);

      return returnValue;
    });
  }

  @Override
  public <T, R> R getValidInput(String promptMsg, Function<String, R> parse,
    Consumer<R> validation, R defaultValue) 
  {
    return exceptionHandler.handle(() -> {
      String input = extractor.extract(promptMsg);
      R returnValue = defaultValue != null && input.isEmpty()
                      ? defaultValue 
                      : parse.apply(input);

      validation.accept(returnValue);

      return returnValue;
    });
  }
}