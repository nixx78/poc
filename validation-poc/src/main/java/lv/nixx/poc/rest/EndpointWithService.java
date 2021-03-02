package lv.nixx.poc.rest;

import lv.nixx.poc.ServiceWithValidator;
import lv.nixx.poc.configuration.AppConfig;
import lv.nixx.poc.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest")
public class EndpointWithService {

    private ServiceWithValidator service;

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



}
