package com.exist.ecc.person.core.service.output.impl;

import java.util.Collection;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.CollectionOutputFormatter;

public class CommaCollectionFormatter<T> 
  extends AbstractCollectionOutputFormatter<T>
{
  
  public CommaCollectionFormatter(
    OutputFormatter<T> elementFormatter)
  {
    super(elementFormatter);
  }

  public String delimiter() {
    return ", ";
  }

}