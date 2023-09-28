package lv.nixx.poc.ratelimit.rest;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucket;
import lv.nixx.poc.ratelimit.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/calculator")
public class CalculatorControllerWithEmbeddedLimiter {

    private static final Logger log = LoggerFactory.getLogger(CalculatorControllerWithEmbeddedLimiter.class);

    private final CalculatorService calculatorService;
    private final LocalBucket builder;

    @Autowired
    public CalculatorControllerWithEmbeddedLimiter(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
        this.builder = Bucket.builder()
                .addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(1))))
                .build();
    }

    @GetMapping("/add")
    public ResponseEntity<Integer> add(@RequestParam("a") int a, @RequestParam("b") int b) {

        ResponseEntity<Integer> integerResponseEntity;
        if (builder.tryConsume(1)) {
            integerResponseEntity = new ResponseEntity<>(calculatorService.calculate(a, b), HttpStatus.OK);
        } else {
            integerResponseEntity = new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        log.info("Response {}", integerResponseEntity);
        return integerResponseEntity;
    }

}
