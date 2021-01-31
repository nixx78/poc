package lv.nixx.poc.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.List;

import static ch.qos.logback.core.spi.FilterReply.DENY;
import static ch.qos.logback.core.spi.FilterReply.NEUTRAL;
import static java.util.Optional.ofNullable;

public class CustomLogbackFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        return List.of(
                ofNullable(event.getFormattedMessage()).orElse(""),
                ofNullable(event.getThrowableProxy()).map(IThrowableProxy::getMessage).orElse("")
        ).stream().anyMatch(t -> t.contains("ABC_MESSAGE")) ? DENY : NEUTRAL;
    }

}
