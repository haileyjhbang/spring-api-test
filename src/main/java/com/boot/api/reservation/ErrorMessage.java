package com.boot.api.reservation;

public class ErrorMessage {
  private String error;

  public String getError() {
    return error;
  }

  public ErrorMessage(String error) {
    this.error = error;
  }
}
