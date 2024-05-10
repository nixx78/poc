package lv.nixx.poc.perfmeeter.rest;

import lv.nixx.poc.perfmeeter.service.ServiceWithMicrometerAnnotations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnotationSandboxController {

    private final ServiceWithMicrometerAnnotations service;

    public AnnotationSandboxController(ServiceWithMicrometerAnnotations service) {
        this.service = service;
    }

    @GetMapping("/counted")
    public String counted() {
        return service.methodWithCounted();
    }

    @GetMapping("/timed")
    public String timed() {
        return service.methodWithTimed();
    }

}
