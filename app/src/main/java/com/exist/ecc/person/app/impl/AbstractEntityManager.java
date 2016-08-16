package com.exist.ecc.person.app.impl;

import com.exist.ecc.person.app.api.EntityManager;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.output.api.OutputWriter;

public abstract class AbstractEntityManager implements EntityManager {

  private InputReader reader;
  private OutputWriter writer;
  private InputExceptionHandler handler;

  public AbstractEntityManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    setReader(reader);
    setWriter(writer);
    setHandler(handler);
  }

  public InputReader getReader() {
    return reader;
  }

  public void setReader(InputReader newReader) {
    reader = newReader;
  }

  public OutputWriter getWriter() {
    return writer;
  }

  public void setWriter(OutputWriter newWriter) {
    writer = newWriter;
  }

  public InputExceptionHandler getHandler() {
    return handler;
  }

  public void setHandler(InputExceptionHandler newHandler) {
    handler = newHandler;
  }

  protected long getId(String entityClass) {
    StringBuilder msg = new StringBuilder("Enter ")
                        .append(entityClass)
                        .append(" ID: ");

    long id = new InputService.Builder<Long>(reader, handler)
              .message(msg.toString())
              .conversion(Long::parseLong)
              .build().getInput();

    return id;
  }

  protected boolean getDeleteConfirmation(String entityClass,
    String entityString)
  {
    String confirmMsg;
    String choice;

    writer.write("");

    confirmMsg = 
      new StringBuilder("Confirm delete of ")
          .append(entityClass)
          .append(" '")
          .append(entityString)
          .append("' (y/n): ")
          .toString();

    choice = 
      new InputService.Builder<String>(reader, handler)
          .message(confirmMsg)
          .build().getInput();

    return choice.equalsIgnoreCase("y");
  }
  
}