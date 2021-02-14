package lv.nixx.poc.features.java10;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalSample {

    @Test(expected = NoSuchElementException.class)
    public void sample() {

        Object v = Optional.empty().orElseThrow(); // Same as get

    }

}
