package com.boot.api.game;

public class ErrorResponse {
  private String error;

  public String getError() {
      return error;
  }

  public ErrorResponse(String error) {
      this.error = error;
  }
}