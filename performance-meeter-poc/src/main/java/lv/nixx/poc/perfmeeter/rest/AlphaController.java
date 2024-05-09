package lv.nixx.poc.perfmeeter.rest;

import lv.nixx.poc.perfmeeter.service.AlphaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlphaController {

    private final AlphaService service;

    public AlphaController(AlphaService service) {
        this.service = service;
    }

    @GetMapping("/process/alpha/{key}")
    public String process(@PathVariable("key") String key) {
        return service.process(key);
    }

}
