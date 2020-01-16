package lv.nixx.poc.features.java12;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

public class TeeningCollectorsSample {

    @Test
    public void sample() {

        double mean = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.teeing(
                        summingDouble(i -> i),
                        counting(),
                        (sum, n) -> sum / n));

        assertEquals(3, mean, 0);
    }
}
