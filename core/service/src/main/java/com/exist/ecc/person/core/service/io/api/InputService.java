package com.exist.ecc.person.core.service.io.api;

import java.util.function.Function;

public interface InputService {

  public abstract <R> R getInput(String promptMsg, Function<String, R> parse);

  public default String getString(String promptMsg) {
    Function<String, String> identity = (str) -> {
      return str;
    };

    return getInput(promptMsg, identity);
  }

  public abstract <T, R> R getValidInput(String promptMsg, Class<T> beanType, 
    String propertyName, Function<String, R> parse);

  public default <T> String getValidString(String promptMsg, Class<T> beanType, 
    String propertyName) 
  {
    Function<String, String> identity = (str) -> {
      return str;
    };

    return getValidInput(promptMsg, beanType, propertyName, identity);
  }

}