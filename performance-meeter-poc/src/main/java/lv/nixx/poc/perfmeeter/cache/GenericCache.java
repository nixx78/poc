package lv.nixx.poc.perfmeeter.cache;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GenericCache {

    private final Map<String, String> map = new HashMap<>();
    private final Random random = new Random();

    private final Timer timer;

    public GenericCache(MeterRegistry meterRegistry, String name) {

        this.timer = Timer.builder("cache." + name)
                .description("Timer for cache: " + name)
                .register(meterRegistry);
    }

    public String get(String key) {

        try {

            return timer.recordCallable(() -> {
                applyDelay();
                return map.get(key);
            });

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    private void applyDelay() {
        int d = random.nextInt(10) + 1;
        try {
            Thread.sleep(d * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

}
