package lv.nixx.poc.ratelimit.rest;

import lv.nixx.poc.ratelimit.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resilience_calculator")
public class CalculatorControllerForResilience {

    private final CalculatorService calculatorService;
    @Autowired
    public CalculatorControllerForResilience(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/add")
    public int add(@RequestParam("a") int a, @RequestParam("b") int b, @RequestParam("user") String user) {
        return calculatorService.calculate(a, b);
    }

}
