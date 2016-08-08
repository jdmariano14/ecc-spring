package com.exist.ecc.person.core.service.io;

import java.util.function.Consumer;
import java.util.function.Function;

import com.exist.ecc.person.core.service.io.api.InputExtractor;
import com.exist.ecc.person.core.service.io.api.InputExceptionHandler;

public class InputService<T> {

  private InputExtractor extractor;
  private InputExceptionHandler exceptionHandler;
  private String message;
  private Function<String, T> conversion;
  private Consumer<T> validation;
  private T defaultValue;

  public InputService(
    final InputExtractor extractor,
    final InputExceptionHandler exceptionHandler,
    final String message,
    final Function<String, T> conversion,
    final Consumer<T> validation,
    final T defaultValue)
  {
    this.extractor = extractor;
    this.exceptionHandler = exceptionHandler;
    this.message = message;
    this.conversion = conversion;
    this.validation = validation;
    this.defaultValue = defaultValue;
  }

  public static class Builder<T> {

    private InputExtractor extractor;
    private InputExceptionHandler exceptionHandler;
    private String message;
    private Function<String, T> conversion;
    private Consumer<T> validation;
    private T defaultValue;

    public Builder(InputExtractor extractor, 
      InputExceptionHandler exceptionHandler) 
    {
      this.extractor = extractor;
      this.exceptionHandler = exceptionHandler;
      message = "";
      conversion = x -> { return (T) x; };
      validation = x -> {};
      defaultValue = null;
    }

    public Builder<T> message(String message) {
      this.message = message;
      return this;
    }

    public Builder<T> conversion(Function<String, T> conversion) {
      this.conversion = conversion;
      return this;
    }

    public Builder<T> validation(Consumer<T> validation) {
      this.validation = validation;
      return this;
    }

    public Builder<T> defaultValue(T defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public InputService<T> build() {
      return new InputService<T>(
        extractor, exceptionHandler, message,
        conversion, validation, defaultValue);
    }

  }


  public T getInput() {
    return exceptionHandler.handle(() -> {
      String input = extractor.extract(message);
      T returnValue = defaultValue != null && input.isEmpty()
                      ? defaultValue 
                      : conversion.apply(input);

      validation.accept(returnValue);

      return returnValue;
    });
  }
}