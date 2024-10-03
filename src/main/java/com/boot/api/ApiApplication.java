package com.boot.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ApiApplication.class, args);


        CsvParser csvParser = new CsvParser();
        csvParser.getHobbies();
        Map<String, List<String>> hobbyMem = csvParser.getHobbyMember();
        Map<String, Integer> hobbyCount = csvParser.getHobbyCount();
        Map<String, Integer> hobbyLastname = csvParser.getHobbyLastname("정");
        long likes = csvParser.getLikes();

    }

}
