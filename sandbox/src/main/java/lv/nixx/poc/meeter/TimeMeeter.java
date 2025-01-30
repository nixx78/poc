package lv.nixx.poc.meeter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class TimeMeeter {

    private static final Logger log = LoggerFactory.getLogger(TimeMeeter.class);

    // Приватный конструктор, чтобы не создавать экземпляр класса
    private TimeMeeter() {}

    // Статический метод для измерения времени без возвращаемого значения
    public static void measure(String message, Runnable codeToExecute) {
        long startTime = System.nanoTime();
        try {
            codeToExecute.run();
        } finally {
            logExecutionTime(message, startTime);
        }
    }

    // Статический метод для измерения времени с возвращаемым значением
    public static <T> T measure(String message, Supplier<T> codeToExecute) {
        long startTime = System.nanoTime();
        try {
            return codeToExecute.get();
        } finally {
            logExecutionTime(message, startTime);
        }
    }

    // Метод для логирования времени
    private static void logExecutionTime(String message, long startTime) {
        long elapsedTime = System.nanoTime() - startTime;
        log.info("Execution of '{}' took {} ms", message, elapsedTime / 1_000_000.0);
    }
}
