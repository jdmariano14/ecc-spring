package com.exist.ecc.person.core.service.input.api;

public interface InputWizard<T> {

  public abstract void setProperties(T baseObject);

}