package com.exist.ecc.person.core.service.io.impl;

import java.util.function.Supplier;

import com.exist.ecc.person.core.service.io.api.InputExceptionHandler;

public class RepeatExtractionExceptionHandler implements InputExceptionHandler {

  public <T> T handle(Supplier<T> extraction) {
    T value = null;

    while (true) {
      try {
        value = extraction.get();
        break;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return value;
  }

}