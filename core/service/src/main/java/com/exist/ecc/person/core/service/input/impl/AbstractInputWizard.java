package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.function.UnaryOperator;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExtractor;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputWizard;

public abstract class AbstractInputWizard<T> implements InputWizard<T> {
  
  private Map<String, InputService.Builder> data;
  private InputExtractor extractor;
  private InputExceptionHandler exceptionHandler;

  public AbstractInputWizard(InputExtractor extractor, 
    InputExceptionHandler exceptionHandler)
  {
    data = new HashMap();
    setExtractor(extractor);
    setExceptionHandler(exceptionHandler);
    
    initializeData(data);
  }

  public InputExtractor getExtractor() {
    return extractor;
  }

  public void setExtractor(InputExtractor newExtractor) {
    extractor = newExtractor;
  }

  public InputExceptionHandler getExceptionHandler() {
    return exceptionHandler;
  }

  public void setExceptionHandler(InputExceptionHandler newExceptionHandler) {
    exceptionHandler = newExceptionHandler;
  }

  public abstract void initializeData(Map<String, InputService.Builder> data);

  public void processMessage(String propertyName, 
    UnaryOperator<String> stringOp)
  {
    data.get(propertyName).message(
      stringOp.apply(data.get(propertyName).getMessage()));
  }

  public void batchProcessMessages(UnaryOperator<String> stringOp) {
    for (String propertyName : data.keySet()) {
      processMessage(propertyName, stringOp);
    }
  }

  public void setProperties(T baseObject) {
    for (String propertyName : data.keySet()) {
      Object inputValue;

      try {
        String defaultValueString =
          BeanUtils.getProperty(baseObject, propertyName);

        data.get(propertyName).defaultValue(
          data.get(propertyName).getConversion().apply(defaultValueString));
      } catch (NullPointerException e) {

      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }

      inputValue = data.get(propertyName).build().getInput();

      try {
        BeanUtils.setProperty(baseObject, propertyName, inputValue); 
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }

}