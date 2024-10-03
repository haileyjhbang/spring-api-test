package com.boot.api;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class CsvParser {
    Map<String, Set<String>> nameHobbies = new HashMap<>();
    List<String> introduces = new ArrayList<>();

    public void getHobbies() {

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

    public Map<String, List<String>> getHobbyMember() {
        Map<String, List<String>> hobbyMember = new HashMap<>();

        nameHobbies.forEach((name, hobbies) -> { ///map에 foreach가능!!
            hobbies.forEach(hobby -> {
                hobbyMember.putIfAbsent(hobby, new ArrayList<>());
                hobbyMember.get(hobby).add(name);
            });
        });

        return hobbyMember;
    }

    public Map<String, Integer> getHobbyCount() {
        return getHobbyMember().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, each -> each.getValue().size()));
    }

    public Map<String, Integer> getHobbyLastname(String lastName) {
//        Map<String, List<String>> hobbyMember = getHobbyMember();
//
//        Map<String, Integer> hobbyLastname = new HashMap<>();
//        for (Map.Entry<String, List<String>> each : hobbyMember.entrySet()) {
//            int cnt = 0;
//            for (String member : each.getValue()) {
//                if (member.startsWith(lastName)) {
//                    cnt++;
//                }
//            }
//            hobbyLastname.put(each.getKey(), cnt);
//        }
//
//        return hobbyLastname;
        return getHobbyMember().entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, each -> (int) each.getValue().stream().filter(member -> member.startsWith(lastName)).count()));


    }

    public long getLikes() {
        return introduces.stream().filter(each -> each.contains("좋아")).count();
    }
}
