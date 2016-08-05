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
  public <R> R getInput(String promptMsg, Function<String, R> parse) {
    return exceptionHandler.handle(() -> {
      return parse.apply(extractor.extract(promptMsg));
    });
  }

  @Override
  public <T, R> R getValidInput(String promptMsg, Function<String, R> parse,
    Consumer<R> validation) 
  {
    return exceptionHandler.handle(() -> {
      R value = parse.apply(extractor.extract(promptMsg));

      validation.accept(value);

      return value;
    });
  }
}