package com.exist.ecc.person.core.service.output.api;

public interface OutputFormatter<T> {

  public abstract String format(T object);

}