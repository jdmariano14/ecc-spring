package com.exist.ecc.person.app;

import java.util.function.Consumer;

import com.exist.ecc.person.util.StringUtil;

public class AppUtil {

  public static Consumer<Integer> optionValidation(int bound) {
    return i -> {
      if (i < 1 || i > bound) {
        throw new IllegalArgumentException(
          "Input does not correspond to an option");
      }
    };
  }

  public static String defaultTransform(String str) {
    return StringUtil.capitalize(
      StringUtil.camelCaseToSpaces(str).toLowerCase());
  }

}