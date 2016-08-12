package com.exist.ecc.person.core.service.input.impl;

import java.util.function.Supplier;

import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;

public class RethrowExceptionHandler implements InputExceptionHandler {

  public <T> T handle(Supplier<T> readOp) {
    T value = null;

    try {
      value = readOp.get();
    } catch (Exception e) {
      throw e;
    }

    return value;
  }

}