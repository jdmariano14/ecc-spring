package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputWizard;

public abstract class AbstractInputWizard<T> implements InputWizard<T> {
  
  private Map<String, PropertyData> data;
  private InputReader reader;
  private InputExceptionHandler exceptionHandler;

  public static class PropertyData {
    private InputService.Builder builder;
    private BiFunction<String, Object, String> format;

    public PropertyData(InputService.Builder builder,
      BiFunction<String, Object, String> format) 
    {
      setBuilder(builder);
      setFormat(format);
    }

    public PropertyData(InputService.Builder builder) {
      this(builder, (s, o) -> {
        return o == null
               ? String.format("%s: ", s)
               : String.format("%s (%s): ", s, o);
      });
    }

    public InputService.Builder getBuilder() {
      return builder;
    }

    public void setBuilder(InputService.Builder newBuilder) {
      builder = newBuilder;
    }

    public BiFunction<String, Object, String> getFormat() {
      return format;
    }

    public void setFormat(BiFunction<String, Object, String> newFormat) {
      format = newFormat;
    }
  }

  public AbstractInputWizard(InputReader reader, 
    InputExceptionHandler exceptionHandler)
  {
    data = new LinkedHashMap();
    setReader(reader);
    setExceptionHandler(exceptionHandler);
    
    initializeData(data);
  }

  public InputReader getReader() {
    return reader;
  }

  public void setReader(InputReader newReader) {
    reader = newReader;
  }

  public InputExceptionHandler getExceptionHandler() {
    return exceptionHandler;
  }

  public void setExceptionHandler(InputExceptionHandler newExceptionHandler) {
    exceptionHandler = newExceptionHandler;
  }

  public void setFormat(String propertyName, 
    BiFunction<String, Object, String> format) 
  {
    data.get(propertyName).setFormat(format);
  }

  public void setDefaultFormat(BiFunction<String, Object, String> format) {
    for (PropertyData propertyData : data.values()) {
      propertyData.setFormat(format);
    }
  }

  public abstract void initializeData(Map<String, PropertyData> data);

  public void setProperties(T baseObject) {
    useExistingValues(baseObject);
    processMessages();

    for (String propertyName : data.keySet()) {
      Object inputValue =
        data.get(propertyName).getBuilder().build().getInput();

      try {
        if (inputValue.toString().equals("\\null")) {
          BeanUtils.setProperty(baseObject, propertyName, null); 
        } else {
          BeanUtils.setProperty(baseObject, propertyName, inputValue);
        }
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }

   private void useExistingValues(T baseObject) {
    for (String propertyName : data.keySet()) {
      try {
        String defaultValueString =
          BeanUtils.getProperty(baseObject, propertyName);

        data.get(propertyName).getBuilder().defaultValue(
          data.get(propertyName)
          .getBuilder()
          .getConversion()
          .apply(defaultValueString));
      } catch (NullPointerException e) {

      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }

  private void processMessages() {
    for (PropertyData propertyData : data.values()) {
      propertyData.getBuilder().message(
        propertyData.getFormat().apply(
          propertyData.getBuilder().getMessage(),
          propertyData.getBuilder().getDefaultValue()));
    }
  }

}