package lv.nixx.poc.rest;

import lv.nixx.poc.ServiceWithValidator;
import lv.nixx.poc.configuration.AppConfig;
import lv.nixx.poc.configuration.YmlSettings;
import lv.nixx.poc.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("rest")
public class EndpointWithService {

    private final ServiceWithValidator service;

    @Autowired
    public EndpointWithService(ServiceWithValidator service) {
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
        return service.getAppConfig();
    }

    @GetMapping(value = "/ymlConfiguration")
    public Map<String, Object> getYamlConfiguration() {
        YmlSettings s = service.getSettingsFromYaml();

        return Map.of(
                "name", s.getName(),
                "aliases", s.getAliases()
        );
    }

}
