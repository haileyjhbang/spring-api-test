package com.boot.api.test1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  private final String DATA_DIR = "data";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new JavaTimeModule());
  }

  @GetMapping("/api/reservation/search")
  // 여기에 코드를 작성하세요.
  public ResponseEntity<?> login(@Valid @ModelAttribute ReservationRequest request) {
  //  if(bindingResult.hasErrors()){
  //    return ResponseEntity.badRequest().body(new ErrorMessage(bindingResult.getFieldError().getDefaultMessage()));
  //  }

    try {
      List<ReservationResponse> users = objectMapper.readValue(
          Files.readAllBytes(Paths.get(DATA_DIR, "reservation.json")),
          new TypeReference<>() {
          });
      Collections.sort(users, Comparator.comparing(ReservationResponse::getCheckIn));

      if (request.getCustomerName().equals("all")) {
        return ResponseEntity.ok(users);
      }

      List<ReservationResponse> response = users.stream()
          .filter(user -> user.getCustomerName().contains(request.getCustomerName()))
          .collect(Collectors.toUnmodifiableList());

      // Map<String, List<ReservationResponse>> userMap = new HashMap<>();
      // for (ReservationResponse response : users) {
      // userMap.putIfAbsent(response.getCustomerName(), new ArrayList<>());
      // userMap.get(response.getCustomerName()).add(response);
      // }

      // if (request.getCustomerName().equals("all")) {
      // return ResponseEntity.ok(users);
      // }
      // if (userMap.get(request.getCustomerName()) == null) {
      // return ResponseEntity.ok(Collections.emptyList());
      // }
      // return ResponseEntity.ok(userMap.get(request.getCustomerName()));

      return ResponseEntity.ok(response);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
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
}
