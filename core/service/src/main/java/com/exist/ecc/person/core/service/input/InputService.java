package com.exist.ecc.person.core.service.input;

import java.util.function.Consumer;
import java.util.function.Function;

import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;

public class InputService<T> {

  private InputReader reader;
  private InputExceptionHandler handler;
  private String message;
  private Function<String, T> conversion;
  private Consumer<T> validation;
  private T defaultValue;

  public InputService(
    final InputReader reader,
    final InputExceptionHandler handler,
    final String message,
    final Function<String, T> conversion,
    final Consumer<T> validation,
    final T defaultValue)
  {
    this.reader = reader;
    this.handler = handler;
    this.message = message;
    this.conversion = conversion;
    this.validation = validation;
    this.defaultValue = defaultValue;
  }

  public static class Builder<T> {

    private InputReader reader;
    private InputExceptionHandler handler;
    private String message;
    private Function<String, T> conversion;
    private Consumer<T> validation;
    private T defaultValue;

    public Builder(InputReader reader, 
      InputExceptionHandler handler) 
    {
      this.reader = reader;
      this.handler = handler;
      message = "";
      conversion = x -> { return (T) x; };
      validation = x -> {};
      defaultValue = null;
    }

    public String getMessage() {
      return message;
    }

    public Builder<T> message(String message) {
      this.message = message;
      return this;
    }

    public Function<String, T> getConversion() {
      return conversion;
    }

    public Builder<T> conversion(Function<String, T> conversion) {
      this.conversion = conversion;
      return this;
    }

    public Consumer<T> getValidation() {
      return validation;
    }

    public Builder<T> validation(Consumer<T> validation) {
      this.validation = validation;
      return this;
    }

    public T getDefaultValue() {
      return defaultValue;
    }

    public Builder<T> defaultValue(T defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }
    
    public InputService<T> build() {
      return new InputService<T>(
        reader, handler, message,
        conversion, validation, defaultValue);
    }
  }

  public T getInput() {
    return handler.handle(() -> {
      String input = reader.read(message);
      T returnValue = null;

      if (input.isEmpty()) {
        if (defaultValue != null) {
          returnValue = defaultValue;
        } else {
          returnValue = conversion.apply(input);  
        }
      } else {
        returnValue = conversion.apply(input);
      }

      validation.accept(returnValue);

      return returnValue;
    });
  }
}