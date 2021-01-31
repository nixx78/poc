package lv.nixx.poc.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.List;
import java.util.Optional;

public class CustomLogbackFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {

        final String message = Optional.ofNullable(event.getFormattedMessage()).orElse("");

        final String exception = Optional.ofNullable(event.getThrowableProxy())
                .map(IThrowableProxy::getMessage)
                .orElse("");

        return List.of(message, exception)
                .stream()
                .anyMatch(t -> t.contains("ABC_MESSAGE")) ? FilterReply.DENY : FilterReply.NEUTRAL;

    }

}
