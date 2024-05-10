package lv.nixx.poc.perfmeeter.service;

import io.micrometer.core.annotation.Counted;
import org.springframework.stereotype.Service;

@Service
public class ServiceWithMicrometerAnnotations {

    @Counted(value = "service.counted")
    public String methodWithCounted() {
        return "Counted.Processed" + System.currentTimeMillis();
    }

    @Counted(value = "service.processed")
    public String methodWithTimed() {
        return "Timed.Processed" + System.currentTimeMillis();
    }

}
