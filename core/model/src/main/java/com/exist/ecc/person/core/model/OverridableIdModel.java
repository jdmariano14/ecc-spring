package com.exist.ecc.person.core.model;

import java.io.Serializable;

public abstract class OverridableIdModel<I extends Serializable> {

  private I overrideId;

  public I getOverrideId() {
    return overrideId;
  }

  public void setOverrideId(I overrideId) {
    this.overrideId = overrideId;
  }
  
}