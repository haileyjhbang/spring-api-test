package com.boot.api.test1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class FileParseUtil {
  private static final String DATA_DIR = "data";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new JavaTimeModule());
  }

  public static <T> List<T> getList(Class<T> clazz, String fileName) {
    try {
      return objectMapper.readValue(
          Files.readAllBytes(Paths.get(DATA_DIR, fileName)),
         TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));

    } catch (IOException e) {
      return Collections.emptyList();
    }
  }
}
