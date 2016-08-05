package com.exist.ecc.person.core.service.io.api;

public interface InputService {

  public abstract String getString(String promptMsg);

  public abstract int getInt(String promptMsg);

  public abstract long getLong(String promptMsg);

  public abstract String getValidInput(String promptMsg);

}