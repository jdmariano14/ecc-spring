package com.exist.ecc.person.core.service.output.impl;

import java.util.Collection;
import java.util.Iterator;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.CollectionFormatter;

public class CollectionFormatterImpl<T> implements CollectionFormatter<T> {

  public static final String DEFAULT_DELIMITER = " ";

  private String delimiter;
  private OutputFormatter<T> elementFormatter;

  public CollectionFormatterImpl(OutputFormatter<T> elementFormatter) {
    setElementFormatter(elementFormatter);
    setDelimiter(DEFAULT_DELIMITER);
  }

  public String getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
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
        collectionString.append(delimiter); 
      }
    }

    return collectionString.toString();
  }
  
}