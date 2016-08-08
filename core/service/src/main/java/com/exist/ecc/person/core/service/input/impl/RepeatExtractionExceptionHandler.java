package com.exist.ecc.person.core.service.input.impl;

import java.util.function.Supplier;

import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;

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