package lv.nixx.poc.meeter;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class StageTimeMeeterTest {


    @Test
    void stageMeeterTest() {

        StageTimeMeeter st = new StageTimeMeeter();

        st.measure("First phase", () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        st.measure("Second phase", () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        st.measure("Third phase", () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        st.logAll();


    }


}
