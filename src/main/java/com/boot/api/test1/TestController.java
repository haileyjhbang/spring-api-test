package com.boot.api.test1;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/api/reservation/search")
  public ResponseEntity<?> login(@Valid @ModelAttribute ReservationRequest request) throws IOException{
      List<ReservationResponse> users = FileParseUtil.getList(ReservationResponse.class, "reservation.json");
      Collections.sort(users, Comparator.comparing(ReservationResponse::getCheckIn));

      if (request.getCustomerName().equals("all")) {
        return ResponseEntity.ok(users);
      }

      List<ReservationResponse> response = users.stream()
          .filter(user -> user.getCustomerName().contains(request.getCustomerName()))
          .collect(Collectors.toUnmodifiableList());

      return ResponseEntity.ok(response);
  }

  @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleValidationException(Exception exception) {
    String message = exception.getMessage();
    if (exception instanceof MethodArgumentNotValidException) {
      message = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldError().getDefaultMessage();
    }
    if (exception instanceof BindException) {
      message = ((BindException) exception).getBindingResult().getFieldError().getDefaultMessage();
    }
    return new ErrorMessage(message);
  }

  @ExceptionHandler(IOException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage handleInternalServerError(IOException exception){
    return new ErrorMessage("please check json file");
  }
}
