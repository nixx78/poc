package lv.nixx.poc.rest;

import lv.nixx.poc.ApplicationPropertiesService;
import lv.nixx.poc.ServiceWithValidator;
import lv.nixx.poc.configuration.AppConfig;
import lv.nixx.poc.configuration.YmlSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("rest")
public class ConfigurationController {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationController.class);

    private final ServiceWithValidator service;
    private final Environment environment;
    private final ApplicationPropertiesService applicationPropertiesService;

    @Autowired
    public ConfigurationController(Environment environment, ServiceWithValidator service, ApplicationPropertiesService applicationPropertiesService) {
        this.environment = environment;
        this.service = service;
        this.applicationPropertiesService = applicationPropertiesService;
    }

    @GetMapping("/configuration")
    public AppConfig getConfiguration() {
        log.info("Active profiles [{}]", Arrays.toString(this.environment.getActiveProfiles()));
        log.info("Custom property [{}]", this.environment.getProperty("base.with.custom"));
        log.info("Property for profile [{}]", applicationPropertiesService.getPropertyForProfile());
        return service.getAppConfig();
    }


    @GetMapping(value = "/ymlConfiguration")
    public Map<String, Object> getYamlConfiguration() {
        YmlSettings s = service.getSettingsFromYaml();

        return Map.of(
                "name", s.getName(),
                "aliases", s.getAliases(),
                "accountCache", s.getAccountCache(),
                "caches", s.getCaches(),
                "mapWithRoles", s.getRoles(),
                "sequence", s.getSequence(),
                "map", s.getMap()
        );
    }

}
