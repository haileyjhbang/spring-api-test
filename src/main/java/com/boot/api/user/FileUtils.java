package com.boot.api.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FileUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static String PATH = "data"; //src 부터 따는거ㅁ

    public static <T> List<T> getListFromJson(String fileName, Class<T> clazz){ ////
        try{
            Path path = Paths.get(PATH, fileName);
            byte[] bytes = Files.readAllBytes(path);
            return objectMapper.readValue(bytes, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz)); ///
        }catch(IOException exception){
            log.warn("file parsing error: {}", exception.getMessage());
        }
        return Collections.emptyList();
    }
}
