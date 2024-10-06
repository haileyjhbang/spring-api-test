package com.boot.api.test1;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationResponse {
  private int id;
  @JsonProperty("customer_name")
  private String customerName;
  @DateTimeFormat(pattern = "YYYY-MM-DD") //라이브러리 필요
  @JsonProperty("check_in")
  private LocalDate checkIn;
  @DateTimeFormat(pattern = "YYYY-MM-DD")
  @JsonProperty("check_out")
  private LocalDate checkOut;
  private Status status;

  public ReservationResponse(){}

  // public ReservationResponse(int id, String customerName, LocalDate checkIn, LocalDate checkOut, Status status) {
  //     this.id = id;
  //     this.customerName = customerName;
  //     this.checkIn = checkIn;
  //     this.checkOut = checkOut;
  //     this.status = status;
  // }



  public static enum Status{
      confirmed, cancelled;
  }



  public int getId() {
      return id;
  }



  public String getCustomerName() {
      return customerName;
  }



  public LocalDate getCheckIn() {
      return checkIn;
  }



  public LocalDate getCheckOut() {
      return checkOut;
  }



  public Status getStatus() {
      return status;
  }
}
