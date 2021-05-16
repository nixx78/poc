package lv.nixx.poc.rest;

import lv.nixx.poc.ServiceWithValidator;
import lv.nixx.poc.configuration.AppConfig;
import lv.nixx.poc.configuration.YmlSettings;
import lv.nixx.poc.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("rest")
public class EndpointWithService {

    private static final Logger log = LoggerFactory.getLogger(EndpointWithService.class);

    private final ServiceWithValidator service;
    private final Environment environment;

    @Autowired
    public EndpointWithService(Environment environment, ServiceWithValidator service) {
        this.environment = environment;
        this.service = service;
    }

    @PostMapping("/service/{id}/{name}")
    public String process(@PathVariable String id, @PathVariable(required = false) String name) {
        return service.process(new Request()
                .setId(id)
                .setName(name)
        );
    }

    @GetMapping("/configuration")
    public AppConfig getConfiguration() {
        log.info("Custom property [{}]", this.environment.getProperty("base.with.custom"));
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
