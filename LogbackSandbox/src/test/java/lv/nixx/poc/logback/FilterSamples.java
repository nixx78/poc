package lv.nixx.poc.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterSamples {

    private static final Logger log = LoggerFactory.getLogger(FilterSamples.class);

    private static final String XYZ_MESSAGE = "XYZ_MESSAGE";
    private static final String ABC_MESSAGE = "ABC_MESSAGE";

    @Test
    public void xyzMessageSample() {
        log.error("Error message [{}]", XYZ_MESSAGE, new IllegalStateException(XYZ_MESSAGE));
        log.error("Error message", new IllegalStateException(XYZ_MESSAGE));   // Filter don't work, text in error
        log.info("Error message [{}]", XYZ_MESSAGE);
    }

    @Test
    public void abcMessageSample() {
        log.error("Error message [{}]", ABC_MESSAGE, new IllegalStateException(ABC_MESSAGE));
        log.error("Error message", new IllegalStateException("Exception:" + ABC_MESSAGE));   // Filter don't work, text in error
        log.info("Error message [{}]", ABC_MESSAGE);
    }

}
