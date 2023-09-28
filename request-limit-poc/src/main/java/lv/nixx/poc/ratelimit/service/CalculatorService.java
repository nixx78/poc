package lv.nixx.poc.ratelimit.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public int calculate(int a, int b) {
        return a + b;
    }

}
