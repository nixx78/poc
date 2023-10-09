package lv.nixx.poc.ratelimit.rest;

import lv.nixx.poc.ratelimit.service.AnnotatedCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annotated")
public class ControllerForAnnotatedService {

    private final AnnotatedCalculatorService annotatedCalculatorService;

    @Autowired
    public ControllerForAnnotatedService(AnnotatedCalculatorService annotatedCalculatorService) {
        this.annotatedCalculatorService = annotatedCalculatorService;
    }

    @GetMapping("/add")
    public int add(@RequestParam("a") int a, @RequestParam("b") int b) {
        return annotatedCalculatorService.calculate(a, b);
    }

    @GetMapping("/addForAdmin")
    public int addForAdmin(@RequestParam("a") int a, @RequestParam("b") int b) {
        return annotatedCalculatorService.calculateByAdmin(a, b);
    }

    @GetMapping("/addWithFallback")
    public int addWithFallback(@RequestParam("a") int a, @RequestParam("b") int b) {
        return annotatedCalculatorService.calculateWithFallback(a, b);
    }

}
