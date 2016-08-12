package com.exist.ecc.person.core.service.input.impl;

import java.util.function.Supplier;

import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;

public class ReturnNullHandler implements InputExceptionHandler {

  public <T> T handle(Supplier<T> readOp) {
    T value = null;

    try {
      value = readOp.get();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }

}