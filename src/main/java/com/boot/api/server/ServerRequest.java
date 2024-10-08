package com.boot.api.server;

import javax.validation.constraints.NotBlank;

public class ServerRequest {
  @NotBlank
  private String name;
  @NotBlank
  private String message;

  public String getName(){
    return name;
  }

  public String getMessage(){
    return message;
  }

  public ServerRequest(){

  }

}
