package lv.nixx.poc.perfmeeter.rest;

import io.micrometer.core.annotation.Counted;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerWithCounted {

    @GetMapping("/counted")
    @Counted(value = "controller.counted")
    public String counted() {
        return "Counted:" + System.currentTimeMillis();
    }

}
