package lv.nixx.poc.perfmeeter.rest;

import lv.nixx.poc.perfmeeter.service.BetaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BetaController {

    private final BetaService service;

    public BetaController(BetaService service) {
        this.service = service;
    }

    @GetMapping("/process/beta/{key}")
    public String process(@PathVariable("key") String key) {
        return service.process(key);
    }

}
