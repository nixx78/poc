package lv.nixx.poc.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleWithMessageFilter {

    private static final Logger log = LoggerFactory.getLogger(SampleWithMessageFilter.class);

    @Test
    public void message() {

        log.error("Error message [{}]", "XYZ_MESSAGE", new IllegalStateException("XYZ_MESSAGE"));
        log.info("Error message [{}]", "XYZ_MESSAGE");

    }

}
