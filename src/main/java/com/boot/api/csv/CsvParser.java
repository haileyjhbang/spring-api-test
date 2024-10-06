package com.boot.api.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;

import lombok.Getter;

@Getter
public class CsvParser {
  private Map<String, Set<String>> nameHobbies = new HashMap<>();
  private List<String> introduces = new ArrayList<>();

  public CsvParser() {
    this.init();
  }

  private void init() {
    ClassPathResource resource = new ClassPathResource("member.csv");

    try (InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

        BufferedReader reader = new BufferedReader(inputStreamReader);) {
      String headers = reader.readLine();
      String line;
      while ((line = reader.readLine()) != null) {
        String[] splitted = line.split(",");
        if (splitted.length >= 3) {
          nameHobbies.put(splitted[0].trim(), Set.of(splitted[1].trim().split(":")));
          introduces.add(splitted[2].trim());
        }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
