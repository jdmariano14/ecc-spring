package com.exist.ecc.person.util;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PromptUtil {
  private static Scanner scanner;

  public static void setScanner(Scanner newScanner) {
    scanner = newScanner;
  }

  public static int promptForInt(String promptMsg) {
    Integer input = null;

    do {
      try {
        input = Integer.parseInt(promptForLine(promptMsg));
      } catch (NumberFormatException e) {
        System.out.println("Please enter an integer value.");
      }
    } while (input == null);

    return input;
  }

  public static long promptForLong(String promptMsg) {
    Long input = Long.MIN_VALUE;

    do {
      try {
        input = Long.parseLong(promptForLine(promptMsg));
      } catch (NumberFormatException e) {
        System.out.println("Please enter an integer value.");
      }
    } while (input == null);

    return input;
  }

  public static String promptForLine(String promptMsg) {
    if (scanner == null) {
      throw new RuntimeException("scanner has not been set");
    }

    System.out.print(promptMsg);

    return scanner.nextLine();
  }

  public static String promptForValidField(String promptMsg, Function<String, List<String>> validator, InvalidInputStrategy strat) {
    String fieldInput = null;

    while (true) {
      List<String> errors = new ArrayList();

      fieldInput = promptForLine(promptMsg);
      errors.addAll(validator.apply(fieldInput));

      if (errors.size() > 0) {
        StringBuilder errMsg = new StringBuilder();

        switch (strat) {
          case THROW_EXCEPTION:
            errMsg.append("Exception caused by");
          case RETURN_NULL:
            errMsg.append("Operation aborted due to ");
          case ASK_AGAIN:
            errMsg.append("Please correct ");
        }

        errMsg.append("the ff. errors: ");
        errMsg.append(errors.stream().collect(Collectors.joining(", ")));

        switch (strat) {
          case THROW_EXCEPTION:
            throw new RuntimeException(errMsg.toString());
          case RETURN_NULL:
            System.out.println(errMsg.toString());
            return null;
          case ASK_AGAIN:
            System.out.println(errMsg.toString());
            continue;
        }
      } else {
        break;
      }
    }

    return fieldInput;
  }

}