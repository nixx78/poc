package lv.nixx.poc.first.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PropertiesService {

    @Value("${first.property}")
    String firstProperty;

    @Value("${second.property}")
    String secondProperty;

    @GetMapping("/applicationProperties")
    public Map<String, Object> showProperties() {
        return Map.of(
                "firstProperty", firstProperty,
                "secondProperty", secondProperty
        );
    }

}
