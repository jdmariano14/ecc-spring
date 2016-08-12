package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.service.output.api.OutputWriter;

public class ConsoleOutputWriter implements OutputWriter {

  public void write(String output) {
    System.out.println(output);
  }

}