package lv.nixx.poc.perfmeeter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class MeterSandbox {

    final MeterRegistry meterRegistry = new SimpleMeterRegistry();

    @Test
    void timerSample() throws Exception {

        Timer timer = Timer.builder("timer.for.method")
                .description("Timer sandbox meter")
                .register(meterRegistry);

        timer.recordCallable(this::methodToMeter);
        timer.recordCallable(this::methodToMeter);
        timer.recordCallable(this::methodToMeter);

        System.out.println("Total time:" + timer.totalTime(TimeUnit.MILLISECONDS));
        System.out.println("Count:" + timer.count());

        System.out.println("--------------------------");

        Timer timerFromRegistry = meterRegistry.find("timer.for.method").timer();
        System.out.println("Total time:" + timerFromRegistry.totalTime(TimeUnit.MILLISECONDS));
        System.out.println("Count:" + timerFromRegistry.count());
    }

    @Test
    void countedSample() {
        Counter counter = Counter.builder("counter.for.method")
                .description("Counter for sandbox meter")
                .register(meterRegistry);

        counter.increment();
        counter.increment();
        counter.increment();
        counter.increment();

        Counter counterFromRegistry = meterRegistry.find("counter.for.method").counter();
        System.out.println("Count:" + counterFromRegistry.count());
    }

    @Test
    void startStopSample() throws InterruptedException {

        Timer timer = Timer.builder("start.stop.timer")
                .description("Start/Stop timer")
                .register(meterRegistry);


        Timer.Sample start = Timer.start(meterRegistry);
        TimeUnit.MILLISECONDS.sleep(50);
        start.stop(timer);

        Timer.Sample start1 = Timer.start(meterRegistry);
        TimeUnit.MILLISECONDS.sleep(50);
        start1.stop(timer);

        System.out.println("Total time:" + timer.totalTime(TimeUnit.MILLISECONDS));
        System.out.println("Count:" + timer.count());
    }

    private String methodToMeter() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(50);
        return "p";
    }

}
