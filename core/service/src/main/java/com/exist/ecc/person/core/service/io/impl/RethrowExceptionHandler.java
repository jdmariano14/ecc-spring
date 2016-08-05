package com.exist.ecc.person.core.service.io.impl;

import java.util.function.Supplier;

import com.exist.ecc.person.core.service.io.api.InputExceptionHandler;

public class RethrowExceptionHandler implements InputExceptionHandler {

  public <T> T handle(Supplier<T> extraction) {
    T value = null;

    try {
      value = extraction.get();
    } catch (Exception e) {
      throw e;
    }

    return value;
  }

}