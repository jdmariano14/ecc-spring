package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputWizard;

public abstract class AbstractWizard<T> implements InputWizard<T> {
  
  private final Map<String, PropertyData> data;
  private InputReader reader;
  private InputExceptionHandler handler;

  static {
    BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
  }

  public static class PropertyData {
    private InputService.Builder builder;
    private Function<String, String> blankFormat;
    private BiFunction<String, Object, String> filledFormat;

    public PropertyData(InputService.Builder builder,
      Function<String, String> blankFormat,
      BiFunction<String, Object, String> filledFormat) 
    {
      setBuilder(builder);
      setBlankFormat(blankFormat);
      setFilledFormat(filledFormat);
    }

    public PropertyData(InputService.Builder builder) {
      this(builder, s -> String.format("%s: ", s),
        (s, o) -> String.format("%s (%s): ", s, o));
    }

    public InputService.Builder getBuilder() {
      return builder;
    }

    public void setBuilder(InputService.Builder newBuilder) {
      builder = newBuilder;
    }

    public Function<String, String> getBlankFormat() {
      return blankFormat;
    }

    public void setBlankFormat(Function<String, String> newFormat) {
      blankFormat = newFormat;
    }

    public BiFunction<String, Object, String> getFilledFormat() {
      return filledFormat;
    }

    public void setFilledFormat(BiFunction<String, Object, String> newFormat) {
      filledFormat = newFormat;
    }
  }

  public AbstractWizard(InputReader reader, InputExceptionHandler handler) {
    data = new LinkedHashMap();
    setReader(reader);
    setDefaultHandler(handler);
    
    initializeData(data);
  }

  public Map<String, PropertyData> getData() {
    return data;
  }

  public InputReader getReader() {
    return reader;
  }

  public void setReader(InputReader newReader) {
    reader = newReader;
  }

  public InputExceptionHandler getDefaultHandler() {
    return handler;
  }

  public void setDefaultHandler(InputExceptionHandler newHandler) {
    handler = newHandler;
  }

  public void setFormat(String propertyName,
    Function<String, String> blankFormat,
    BiFunction<String, Object, String> filledFormat)
  {
    data.get(propertyName).setBlankFormat(blankFormat);
    data.get(propertyName).setFilledFormat(filledFormat);
  }

  public void setDefaultFormat(Function<String, String> blankFormat,
    BiFunction<String, Object, String> filledFormat)
  {
    for (PropertyData propertyData : data.values()) {
      propertyData.setBlankFormat(blankFormat);
      propertyData.setFilledFormat(filledFormat);
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
        if (inputValue == null || inputValue.toString().equals("\\null")) {
          BeanUtils.setProperty(baseObject, propertyName, null); 
        } else {
          BeanUtils.setProperty(baseObject, propertyName, inputValue);
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
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
        throw new RuntimeException(e);
      }
    }
  }

  private void processMessages() {
    for (PropertyData propertyData : data.values()) {
      InputService.Builder builder = propertyData.getBuilder();

      if (builder.getDefaultValue() == null) {
        builder.message(propertyData.getBlankFormat().apply(
          builder.getMessage()));
      } else {
        builder.message(propertyData.getFilledFormat().apply(
          builder.getMessage(), builder.getDefaultValue()));
      }
    }
  }

}