package com.boot.api.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CsvService {

  public Map<String, List<String>> getHobbyMember(CsvParser parser) {
    Map<String, List<String>> hobbyMember = new HashMap<>();

    parser.getNameHobbies().forEach((name, hobbies) -> { /// map에 foreach가능!!
      hobbies.forEach(hobby -> {
        hobbyMember.putIfAbsent(hobby, new ArrayList<>());
        hobbyMember.get(hobby).add(name);
      });
    });

    return hobbyMember;
  }

  public Map<String, Integer> getHobbyCount(CsvParser parser) {
    return getHobbyMember(parser).entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, each -> each.getValue().size()));
  }

  public Map<String, Integer> getHobbyLastname(CsvParser parser, String lastName) {
    // Map<String, List<String>> hobbyMember = getHobbyMember();
    //
    // Map<String, Integer> hobbyLastname = new HashMap<>();
    // for (Map.Entry<String, List<String>> each : hobbyMember.entrySet()) {
    // int cnt = 0;
    // for (String member : each.getValue()) {
    // if (member.startsWith(lastName)) {
    // cnt++;
    // }
    // }
    // hobbyLastname.put(each.getKey(), cnt);
    // }
    //
    // return hobbyLastname;
    return getHobbyMember(parser).entrySet().stream().collect(
        Collectors.toMap(Map.Entry::getKey,
            each -> (int) each.getValue().stream().filter(member -> member.startsWith(lastName)).count()));
  }

  public long getLikes(CsvParser parser) {
    return parser.getIntroduces().stream().filter(each -> each.contains("좋아")).count();
  }
}
