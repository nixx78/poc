package lv.nixx.poc.first.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PropertiesController {

    @Value("${first.security.property}")
    String firstProperty;

    @Value("${second.security.property}")
    String secondProperty;

    @Value("${management.health.diskspace.enabled}")
    boolean diskspaceEnnabled;

    @GetMapping("/applicationProperties")
    public Map<String, Object> showProperties() {
        return Map.of(
                "firstSecurityProperty", firstProperty,
                "secondSecurityProperty", secondProperty,
                "diskspaceEnnabled", diskspaceEnnabled
        );
    }

}
