package com.boot.api.game;

import java.io.IOException;
import java.net.BindException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.NameNotFoundException;

import com.boot.api.reservation.ErrorMessage;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GameController {
  private final String DATA_DIR = "data";
  private final ObjectMapper objectMapper = new ObjectMapper();

  @GetMapping("/api/gamerecord/users")
  public List<UserResponse> getUsers() throws StreamReadException, DatabindException, IOException {
    return getUserList().stream()
        .sorted(Comparator.comparing(GameUser::getTag).thenComparing(Comparator.comparing(GameUser::getUsername)))
        .map(each -> new UserResponse(each.getTag(), each.getUsername()))
        .collect(Collectors.toUnmodifiableList());

  }

  @GetMapping("/api/gamerecord/winrate")
  public WinRateResponse getWinrate(@ModelAttribute WinRateRequest rateRequest)
      throws StreamReadException, DatabindException, IOException, NameNotFoundException {
    Integer winrate = getUserList().stream()
        .filter(
            user -> user.getTag().equals(rateRequest.getTag()) && user.getUsername().equals(rateRequest.getUsername()))
        .findFirst().map(each -> each.getWin() * 100/ (each.getWin() + each.getLose())).orElseThrow(() -> new NameNotFoundException());
    return new WinRateResponse(winrate);
  }

  private List<GameUser> getUserList() throws StreamReadException, DatabindException, IOException {
    // ClassPathResource r = new ClassPathResource("path");
    // objectMapper.readValue(r.getInputStream(), TypeFactory.defaultInstance().constructCollectionType(List.class, GameUser.class));

    return objectMapper.readValue(
        Files.readAllBytes(Paths.get(DATA_DIR, "records.json")),
        TypeFactory.defaultInstance().constructCollectionType(List.class, GameUser.class));
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handleValidation(BindException exception) {
    return new ErrorResponse("Invalid data format");
  }

  @ExceptionHandler(NameNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleNotFound(NameNotFoundException exception){
    return new ErrorMessage("data not found");
  }

  @ExceptionHandler({ StreamReadException.class, DatabindException.class, IOException.class })
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleParseException(Exception exception) {
    log.error("handleParseException got exception {}", exception.getMessage());
    return new ErrorResponse(exception.getMessage());
  }
}
