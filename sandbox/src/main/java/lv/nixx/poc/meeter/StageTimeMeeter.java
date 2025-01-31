package lv.nixx.poc.meeter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class StageTimeMeeter {

    private static final Logger log = LoggerFactory.getLogger(StageTimeMeeter.class);

    Map<String, Double> stages = new ConcurrentSkipListMap<>();

    public void measure(String message, Runnable codeToExecute) {
        long startTime = System.nanoTime();
        try {
            codeToExecute.run();
        } finally {
            saveExecutionTime(message, startTime);
        }
    }

    public void logAll() {
        String s = stages.entrySet().stream()
                .map(e -> String.format("\t\t'%s' execution time: %.3f ms", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));

        log.info("Execution timings: \n{}", s);
    }

    private void saveExecutionTime(String message, long startTime) {
        long elapsedTime = System.nanoTime() - startTime;
        stages.put(message, elapsedTime / 1_000_000.0);
    }

}
