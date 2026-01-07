package com.rozp.adv.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> getHealth() {
        // Повертаємо структуру {"status": "UP"}
        return Map.of("status", "healthy");
    }
}
