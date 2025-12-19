package lv.nixx.poc.sandbox;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RetryCounterTest {

    @Test
    public void tryCountSuccessTest() {

        Function<Integer, String> onError = t -> {
            throw new IllegalArgumentException("Error during call");
        };

        RetryCounter<Integer, String> r = new RetryCounter<>(5, t -> t + "Result", onError);
        assertEquals("10Result", r.call(10));
    }

    @Test
    public void tryCountExceptionTest() {

        Function<Integer, String> onError = t -> {
            throw new IllegalArgumentException("Error during call");
        };

        Function<Integer, String> onTry = t -> {
            throw new IllegalArgumentException("Error during call");
        };

        RetryCounter<Integer, String> integerStringRetryCounter = new RetryCounter<>(5, onTry, onError);

        assertThrows(IllegalArgumentException.class, () -> {
            integerStringRetryCounter.call(2);
        });
    }

    @Test
    public void tryCountReturnValueOnFailTest() {

        Function<Integer, String> onError = t -> {
            throw new IllegalArgumentException("Error during call");
        };

        Function<Integer, String> onTry = t -> "FailValue" + t;

        RetryCounter<Integer, String> r = new RetryCounter<>(5, onTry, onError);
        assertEquals("FailValue10", r.call(10));
    }

    static class RetryCounter<K, V> {

        private final Logger LOG = LoggerFactory.getLogger("RetryCounter");

        private final int tryCount;
        private final Function<K, V> onTry;
        private final Function<K, V> onError;

        RetryCounter(int tryCount, Function<K, V> onTry, Function<K, V> onError) {
            this.tryCount = tryCount;
            this.onTry = onTry;
            this.onError = onError;
        }

        public V call(K input) {
            int currentTry = 1;

            while (currentTry <= tryCount) {
                try {
                    LOG.debug("Try [{}] of [{}]", currentTry, tryCount);
                    return onTry.apply(input);
                } catch (Exception ex) {
                    if (currentTry == tryCount) {
                        LOG.error("Error [{}]", ex.getMessage(), ex);
                        return onError.apply(input);
                    }
                    currentTry++;
                }
            }
            return null;
        }

    }

}
