package com.boot.api.reservation;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationResponse {
  private int id;
  @JsonProperty("customer_name")
  private String customerName;
  @JsonFormat(pattern = "yyyy-MM-dd") //라이브러리 필요
  @JsonProperty("check_in")
  private LocalDate checkIn;
  @DateTimeFormat(pattern = "yyyy-MM-dd") //파일에 있는 형식이 이거라서 이렇게 담는거라고 말해주는거
  @JsonProperty("check_out")
  private LocalDate checkOut;
  private Status status;

  public ReservationResponse(){}


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
