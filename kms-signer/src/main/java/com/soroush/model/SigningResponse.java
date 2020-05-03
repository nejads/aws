package com.soroush.model;

public class SigningResponse {

  private final String signature;

  public SigningResponse(String signature) {
    this.signature = signature;
  }

  public String getSignature() {
    return this.signature;
  }

}
