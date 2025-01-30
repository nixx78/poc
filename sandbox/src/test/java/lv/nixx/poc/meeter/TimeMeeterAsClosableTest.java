package lv.nixx.poc.meeter;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeMeeterAsClosableTest {

    @Test
    void calculateTimeWithoutException() {
        TimeMeeterAsClosable.measure("My Process", () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(23);
                System.out.println("My Process -> Finished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals("Response",
                TimeMeeterAsClosable.measure("Process with return value", () -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                        return "Response";
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
    }

    @Test
    void calculateTimeWithException() {
        assertThrows(IllegalStateException.class, () -> TimeMeeterAsClosable.measure("My Process with exception", () -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(5);

                        throw new IllegalStateException("Exception inside executor");

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }), "Exception inside executor"
        );
    }

}
