package com.soroush.model;

public class SigningRequestModel {

  private String message;

  public SigningRequestModel(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
