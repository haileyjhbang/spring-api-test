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
    Set<String> hobbies = new HashSet<>();

    public void getHobbies() {

        ClassPathResource resource = new ClassPathResource("member.csv");

        try (InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

             BufferedReader reader = new BufferedReader(inputStreamReader);) {
            String headers = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(",");
                nameHobbies.put(splitted[0].trim(), Set.of(splitted[1].trim().split(":")));
                introduces.add(splitted[2].trim());
                hobbies.addAll(Set.of(splitted[1].trim().split(":")));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Map<String, List<String>> getHobbyMember() {

        Map<String, List<String>> hobbyMember = new HashMap<>();
        hobbies.forEach(hobby -> {
            nameHobbies.entrySet().stream().filter(nameHobby ->
                    nameHobby.getValue().contains(hobby)
            ).forEach(nameHobby -> {
                hobbyMember.putIfAbsent(hobby, new ArrayList<>());
                hobbyMember.get(hobby).add(nameHobby.getKey());
            });

        });
        return hobbyMember;
    }

    public Map<String, Integer> getHobbyCount() {
        Map<String, List<String>> hobbyMem = getHobbyMember();
        return hobbyMem.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, each -> each.getValue().size()));
    }

    public Map<String, Integer> getHobbyLastname(String lastName) {
        Map<String, Integer> hobbyLastname = new HashMap<>();
        Map<String, List<String>> hobbyMember = getHobbyMember();

        for (Map.Entry<String, List<String>> each : hobbyMember.entrySet()) {
            int cnt = 0;
            for (String member : each.getValue()) {
                if (member.startsWith(lastName)) {
                    cnt++;
                }
            }
            hobbyLastname.put(each.getKey(), cnt);
        }

        return hobbyLastname;
    }

    public long getLikes() {
        return introduces.stream().filter(each -> each.contains("좋아")).count();
    }
}
