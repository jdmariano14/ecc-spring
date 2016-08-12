package com.exist.ecc.person.core.service.output.impl;

import java.util.Collection;
import java.util.Iterator;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.CollectionOutputFormatter;

public abstract class AbstractCollectionOutputFormatter<T> 
  implements CollectionOutputFormatter<T>
{
  private OutputFormatter<T> elementFormatter;

  public AbstractCollectionOutputFormatter(
    OutputFormatter<T> elementFormatter) 
  {
    setElementFormatter(elementFormatter);
  }

  public OutputFormatter<T> getElementFormatter() {
    return elementFormatter;
  }

  public void setElementFormatter(OutputFormatter<T> newElementFormatter) {
    elementFormatter = newElementFormatter;
  }

  @Override
  public String format(Collection<T> collection) {
    StringBuilder collectionString = new StringBuilder();

    for (Iterator<T> iter = collection.iterator(); iter.hasNext(); ) {
      collectionString.append(elementFormatter.format(iter.next()));

      if (iter.hasNext()) {
        collectionString.append(delimiter()); 
      }
    }

    return collectionString.toString();
  }

  public abstract String delimiter();

}