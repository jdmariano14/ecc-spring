package com.exist.ecc.person.core.service.io.api;

import java.util.function.Function;
import java.util.function.Consumer;

public interface InputService {
  static Function<String, String> STRING_IDENTITY = (str) -> {
    return str;
  };

  public abstract <R> R getInput(String promptMsg, Function<String, R> parse,
    R defaultValue);

  public default <R> R getInput(String promptMsg, Function<String, R> parse) {
    return getInput(promptMsg, parse, null);
  }

  public default String getString(String promptMsg) {
    return getInput(promptMsg, STRING_IDENTITY);
  }

  public default String getString(String promptMsg, String defaultValue) {
    return getInput(promptMsg, STRING_IDENTITY, defaultValue);
  }

  public abstract <T, R> R getValidInput(String promptMsg, Function<String, R> parse,
    Consumer<R> validation, R defaultValue);

  public default <T, R> R getValidInput(String promptMsg, Function<String, R> parse,
    Consumer<R> validation) {
    return getValidInput(promptMsg, parse, validation, null);
  }

  public default String getValidString(String promptMsg, Consumer<String> validation)  {
    return getValidInput(promptMsg, STRING_IDENTITY, validation);
  }

  public default String getValidString(String promptMsg, Consumer<String> validation,
    String defaultValue)
  {
    return getValidInput(promptMsg, STRING_IDENTITY, validation, defaultValue);
  }

}