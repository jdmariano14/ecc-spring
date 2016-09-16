package com.exist.ecc.person.core.service.input.impl;

import java.io.InputStream;
import java.util.Scanner;

import com.exist.ecc.person.core.service.input.api.InputService;

public abstract class CsvInputService extends AbstractInputService {
  

  @Override
  public void processInputStream(InputStream inputStream) {
    try {
      Scanner scanner = new Scanner(inputStream);

      while(scanner.hasNext()) {
        processValueArray(scanner.nextLine().split(","));
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

}