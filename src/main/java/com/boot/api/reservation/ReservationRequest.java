package com.boot.api.reservation;

import javax.validation.constraints.NotBlank;

public class ReservationRequest {
  @NotBlank(message = "customerName is required")
  private String customerName;

  public String getCustomerName() {
    return customerName;
  }

  public ReservationRequest(String customerName) {
    this.customerName = customerName;
  }
}
