package lv.nixx.poc.sandbox.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LambdaSandbox {

    @Test
    void calculateTest() {
        assertEquals(30L, calculate((a, b) -> a + b, 10, 20));
        assertEquals(20L, calculate((a, b) -> a - b, 30, 10));
        assertEquals(300L, calculate((a, b) -> a * b, 10, 30));
    }

    @Test
    void timeProviderTest() {
        TimeProvider tp = () -> 1000L;
        assertEquals(1000L, tp.currentTime());
    }

    @Test
    void functionSandbox() {
        Function<Long, String> f = t -> "[" + t + ": apply]";

        assertEquals("[10: apply]", f.apply(10L));
        assertEquals("[2000: apply]: andThen", f.andThen(t -> t + ": andThen").apply(2000L));
    }

    @Test
    void composeSandbox() {
        Function<Integer, Integer> calculate = x -> x + 1;
        Function<Integer, String> toString = x -> "Result: " + x;

        String result = toString.compose(calculate).apply(5);
        assertEquals("Result: 6", result);
    }

    @Test
    void composeAndThenSandbox() {
        Function<Integer, String> toString = x -> "[Calculation result: " + x + "]";

        String result = toString
                .compose((Function<Integer, Integer>) x -> x + 1)
                .compose((Function<Integer, Integer>) x -> x * 100)
                .andThen(this::send)
                .apply(5);

        assertEquals("sendId: [Calculation result: 501]", result);
    }

    @Test
    void biFunctionSample() {
        BiFunction<String, Integer, String> bi = (s, i) -> s + ":" + i;
        assertEquals("Text:777", bi.apply("Text", 777));
    }

    private String send(String v) {
        return "sendId: " + v;
    }

    private int calculate(Calculator calculator, int a, int b) {
        return calculator.calculate(a, b);
    }

    interface Calculator {
        int calculate(int a, int b);
    }

    interface TimeProvider {
        long currentTime();
    }

}
