package lv.nixx.poc.meeter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class TimeMeeterAsClosable implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(TimeMeeter.class);

    private final String message;
    private final long startTime;

    private TimeMeeterAsClosable(String message) {
        this.message = message;
        this.startTime = System.nanoTime();
    }

    private static TimeMeeterAsClosable start(String message) {
        return new TimeMeeterAsClosable(message);
    }

    public static void measure(String message, Runnable codeToExecute) {
        try (TimeMeeterAsClosable ignored = new TimeMeeterAsClosable(message)) {
            codeToExecute.run();
        }
    }

    public static <T> T measure(String message, Supplier<T> codeToExecute) {
        try (TimeMeeterAsClosable ignored = new TimeMeeterAsClosable(message)) {
            return codeToExecute.get();
        }
    }

    @Override
    public void close() {
        long elapsedTime = System.nanoTime() - startTime;
        log.info("Execution of '{}' took {} ms", message, elapsedTime / 1_000_000.0);
    }
}
