package lv.nixx.poc.first.rest;

import lv.nixx.poc.first.service.ServiceForMonitoring;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceForMonitoringController {

    private final ServiceForMonitoring service;

    public ServiceForMonitoringController(ServiceForMonitoring service) {
        this.service = service;
    }

    @GetMapping("/service")
    public ServiceForMonitoring setServiceStatus(
            @RequestParam String status,
            @RequestParam String message,
            @RequestParam String details) {
        service.setStatus(status);
        service.setMessage(message);
        service.setDetails(details);
        return service;
    }

}
