package com.boot.api.csv;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/csv")
@RequiredArgsConstructor
public class CsvController {

  private final CsvService csvService;

  private final CsvParser csvParser = new CsvParser();

  @GetMapping("/hobby-member")
  public ResponseEntity<Map<String, List<String>>> getHobbyMember(CsvParser parser) {
    return ResponseEntity.ok().body(csvService.getHobbyMember(csvParser));
  }

  @GetMapping("/hobby-count")
  public ResponseEntity<Map<String, Integer>> getHobbyCount(CsvParser parser) {
    return ResponseEntity.ok(csvService.getHobbyCount(parser));
  }

  @GetMapping("/hobby-name")
  public ResponseEntity<Map<String, Integer>> getHobbyLastname(CsvParser parser, String lastName) {
    return ResponseEntity.ok(csvService.getHobbyLastname(parser, "정"));
  }

  @GetMapping("/likes")
  public ResponseEntity<Long> getLikes(CsvParser parser) {
    return ResponseEntity.ok(csvService.getLikes(parser));
  }
}
