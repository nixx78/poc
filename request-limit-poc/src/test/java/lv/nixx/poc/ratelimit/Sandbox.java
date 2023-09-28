package lv.nixx.poc.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Sandbox {

    //TODO https://github.com/abdennebi/spring-boot-bucket4j-hazelcast-demo/blob/master/src/main/java/com/abddennebi/demo/filter/IpThrottlingFilter.java
    //TODO https://www.baeldung.com/spring-bucket4j

    @Test
    void test() throws InterruptedException {

        Refill refill = Refill.intervally(10, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(10, refill);
        Bucket bucket = Bucket.builder()
                .addLimit(limit)
                .build();

        for (int i = 1; i <= 10; i++) {
            assertTrue(bucket.tryConsume(1));
        }

        // No token available
        assertFalse(bucket.tryConsume(1));

        TimeUnit.SECONDS.sleep(6);

        // Now, is possible get token again
        assertTrue(bucket.tryConsume(10));

        // No more available tokens
        assertFalse(bucket.tryConsume(1));
    }
}
