package com.example.restdocs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<ApiResult<String>> helloWorld() {
        return ResponseEntity.ok(new ApiResult<>("Hello, World!"));
    }

    @GetMapping("/optional")
    public ResponseEntity<Void> helloOptional(
        @RequestParam String required,
        @RequestParam(required = false) String value) {
        return ResponseEntity.noContent().build();
    }
}
