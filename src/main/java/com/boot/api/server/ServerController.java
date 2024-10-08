package com.boot.api.server;

import javax.validation.Valid;

import com.boot.api.reservation.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @GetMapping("/server-health-check")
    public ResponseEntity<?> serverHealthCheck() {
        return ResponseEntity.ok(new ServerResponse("ok"));
    }

    @PostMapping("/echo")
    public ResponseEntity<?> echo(@RequestBody @Valid ServerRequest body, BindingResult bindingResult) {
      if(bindingResult.hasErrors()){
        return ResponseEntity.badRequest().body(new ErrorMessage("invalid"));
      }
        return ResponseEntity.ok(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidation(MethodArgumentNotValidException exception){
      return new ErrorMessage("Invalid data forma");
    }
}