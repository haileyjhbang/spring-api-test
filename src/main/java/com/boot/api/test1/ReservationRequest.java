package com.boot.api.test1;

import javax.validation.constraints.NotBlank;

public class ReservationRequest {
  @NotBlank(message = "customerName is required")
  private String customerName;

  public String getCustomerName() {
    return customerName;
  }

  // public void setCustomerName(String customerName) {
  //   this.customerName = customerName;
  // }

  public ReservationRequest(String customerName) {
    this.customerName = customerName;
  }
}
