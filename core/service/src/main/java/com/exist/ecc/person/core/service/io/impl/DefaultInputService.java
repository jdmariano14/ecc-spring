package com.exist.ecc.person.core.service.io.impl;

import java.util.Scanner;

import com.exist.ecc.person.core.service.io.api.InputService;
import com.exist.ecc.person.core.service.io.api.InputExtractor;
import com.exist.ecc.person.core.service.io.api.InputExceptionHandler;

public abstract class DefaultInputService implements InputService {

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
  public String getString(String promptMsg) {

    String input = exceptionHandler.handle(() -> {
      return extractor.extract(promptMsg);
    });

    return input;
  }

}