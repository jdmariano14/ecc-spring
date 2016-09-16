package com.exist.ecc.person.core.service.input.api;

import java.io.InputStream;

public interface InputService {

  public abstract void execute(InputStream inputStream, boolean clear);

  public abstract void processInputStream(InputStream inputStream);

  public abstract void processValueArray(String[] values);

}