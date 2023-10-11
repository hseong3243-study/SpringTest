package com.example.restdocs.docs;

import com.example.restdocs.enumtype.EnumType;
import com.example.restdocs.enumtype.Provider;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumDocsController {

    @GetMapping("/provider")
    public ResponseEntity<Map<String, String>> getProvider() {
        return ResponseEntity.ok(getEnumTypes(Provider.values()));
    }

    private Map<String, String> getEnumTypes(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
            .collect(Collectors.toMap(EnumType::getName, EnumType::getValue));
    }
}
