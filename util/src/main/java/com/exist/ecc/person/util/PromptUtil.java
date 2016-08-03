package com.exist.ecc.person.util;

import java.util.Scanner;

public class PromptUtil {
  private static Scanner scanner;

  public static void setScanner(Scanner newScanner) {
    scanner = newScanner;
  }

  public static int promptForInt(String promptMsg) {
    int input = Integer.MIN_VALUE;

    do {
      try {
        input = Integer.parseInt(promptForLine(promptMsg));
      } catch (NumberFormatException e) {
        System.out.println("Please enter an integer value.");
      }
    } while (input == Integer.MIN_VALUE);

    return input;
  }

  public static String promptForLine(String promptMsg) {
    if (scanner == null) {
      throw new RuntimeException("scanner has not been set");
    }

    System.out.print(promptMsg);

    return scanner.nextLine();
  }

}