package com.boot.api.server;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @GetMapping("/server-health-check")
    public ResponseEntity<?> serverHealthCheck() {
        // 여기에 코드를 작성하세요.
        return ResponseEntity.ok().build();
    }

    @PostMapping("/echo")
    public ResponseEntity<?> echo(@RequestBody Map<String, Object> body) {
        // 여기에 코드를 작성하세요.
        return ResponseEntity.ok().build();
    }
}