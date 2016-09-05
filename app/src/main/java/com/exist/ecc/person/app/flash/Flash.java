package com.exist.ecc.person.app.flash;

 public class Flash {

  private String message;

  public Flash(String message) {
    setMessage(message);
  }

  public String getMessage() {
    String tmpMsg = message;
    message = null;
    return tmpMsg;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
}