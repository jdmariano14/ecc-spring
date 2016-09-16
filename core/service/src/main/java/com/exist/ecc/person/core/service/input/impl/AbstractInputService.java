package com.exist.ecc.person.core.service.input.impl;

import java.io.InputStream;

import com.exist.ecc.person.core.service.data.api.DataService;
import com.exist.ecc.person.core.service.input.api.InputService;

public abstract class AbstractInputService implements InputService {

  @Override
  public void execute(InputStream inputStream, boolean clear) {
    if (clear) {
      clearDatabaseTable();
    }

    processInputStream(inputStream);

  }

  protected abstract void clearDatabaseTable();

}