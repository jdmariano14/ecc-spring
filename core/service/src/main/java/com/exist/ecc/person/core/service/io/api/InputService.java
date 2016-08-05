package com.exist.ecc.person.core.service.io.api;

import java.util.function.Function;
import java.util.function.Consumer;

public interface InputService {
  static Function<String, String> STRING_IDENTITY = (str) -> {
    return str;
  };

  public abstract <R> R getInput(String promptMsg, Function<String, R> parse);

  public default String getString(String promptMsg) {
    return getInput(promptMsg, STRING_IDENTITY);
  }

  public abstract <T, R> R getValidInput(String promptMsg, Function<String, R> parse,
    Consumer<R> validation);

  public default String getValidString(String promptMsg, Consumer<String> validation)  {
    return getValidInput(promptMsg, STRING_IDENTITY, validation);
  }

}