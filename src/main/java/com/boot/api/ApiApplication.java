package com.boot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  /////이것도
public class ApiApplication {

    public static void main(String[] args) {
       SpringApplication.run(ApiApplication.class, args); ////이게 없음..ㅎㅎ
    }
}
