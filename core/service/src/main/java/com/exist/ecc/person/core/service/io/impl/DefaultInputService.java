package com.exist.ecc.person.core.service.io.impl;

import java.util.Scanner;

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
  public String getString(String promptMsg) {
    return exceptionHandler.handle(() -> {
      return extractor.extract(promptMsg);
    });
  }

  @Override
  public int getInt(String promptMsg) {
    return exceptionHandler.handle(() -> {
      return Integer.parseInt(extractor.extract(promptMsg));
    });
  }

  @Override
  public long getLong(String promptMsg) {
    return exceptionHandler.handle(() -> {
      return Long.parseLong(extractor.extract(promptMsg));
    });
  }

  @Override
  public String getValidInput(String promptMsg) {
    throw new UnsupportedOperationException();
  }
}