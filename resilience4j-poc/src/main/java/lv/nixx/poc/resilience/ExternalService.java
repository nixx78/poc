package lv.nixx.poc.resilience;

import java.util.concurrent.atomic.AtomicInteger;

public class ExternalService {

    private static AtomicInteger callCount = new AtomicInteger(1);

    public void reset() {
        callCount = new AtomicInteger(1);
    }

    public String process(int data) {
        System.out.println("Process method, data:" + data);
        int c = callCount.getAndIncrement();
        if (c != 3 && data == 200) {
            throw new IllegalStateException("Service not available");
        }

        if (data == 300) {
            throw new IllegalStateException("Service not available, fatal error");
        }

        return "Processed:" + data;
    }

}
