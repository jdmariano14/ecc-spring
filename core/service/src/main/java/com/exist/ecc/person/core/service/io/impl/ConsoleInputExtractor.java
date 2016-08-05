package com.exist.ecc.person.core.service.io.impl;

import java.util.Scanner;

import com.exist.ecc.person.core.service.io.api.InputExtractor;

public class ConsoleInputExtractor implements InputExtractor {

  private Scanner scanner;

  public ConsoleInputExtractor(Scanner scanner) {
    setScanner(scanner);
  }

  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }

  public String extract(String promptMsg) {
    System.out.print(promptMsg);

    return scanner.nextLine();
  }

}