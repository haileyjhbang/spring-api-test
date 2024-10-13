package com.boot.api.customer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public final class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String INPUT_PATH = "../data/input"; //절대경로 나을지도
    private static final String OUTPUT_PATH = "../data/output";
    // private static final String INPUT_PATH = "/home/programmers/project/data/input";
    // private static final String OUTPUT_PATH = "/home/programmers/project/data/output";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> fileToList(String fileName, Class<T> clazz){
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(INPUT_PATH, fileName));
            return objectMapper.readValue(bytes, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.warn("FileUtils.fileToList got error with {} - {}", fileName, e.getMessage());
        }
        return Collections.emptyList();
    }

    public static String objectToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.warn("FileUtils.objectToJson got error with - {}", e.getMessage());
        }
        return null;
    }

    @Deprecated
    public static Path toFile(String fileName, String data){
        Path path = Paths.get(OUTPUT_PATH + fileName);
        try {
            return Files.write(path, data.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.warn("FileUtils.toFile got error with {} - {}", fileName, e.getMessage());
        }
        return null;
    }

    public static void toFile(String fileName, Object data){
        try {
             objectMapper.writeValue(new File(OUTPUT_PATH + fileName), data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.warn("FileUtils.toFile got error with {} - {}", fileName, e.getMessage());
        }
    }
}
