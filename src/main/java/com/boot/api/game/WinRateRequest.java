package com.boot.api.game;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WinRateRequest {
  @NotBlank
  private String username;
  @NotBlank
  private String tag;
}
