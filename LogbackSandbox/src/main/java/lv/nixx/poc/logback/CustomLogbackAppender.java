package lv.nixx.poc.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class CustomLogbackAppender extends AppenderBase<ILoggingEvent> {

    private String param1;
    private String param2;

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        System.out.println("Event object :" + eventObject + " param1:" + param1 + " param2:" + param2);
    }

}
