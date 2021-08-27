package lv.nixx.poc.resilience;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static org.junit.Assert.assertThrows;

public class ExternalServiceRetrySandbox {

    private final ExternalService externalService = new ExternalService();
    private final Retry retry;

    @Before
    public void init() {
        externalService.reset();
    }

    public ExternalServiceRetrySandbox() {

        retry = Retry.of("externalCallRetry", RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofMillis(100L))
                .retryOnException(e -> e instanceof IllegalStateException)
                .retryOnResult(e -> e.equals("Fail"))
                .build()
        );

        Retry.EventPublisher ep = retry.getEventPublisher();
        ep.onEvent(e ->
                System.out.println("Retry event, type:" + e.getEventType() + ", try:" + e.getNumberOfRetryAttempts() + " error: " + e.getLastThrowable())
        );

    }

    @Test
    public void callSuccessTest() {
        CheckedFunction0<String> cf = Retry.decorateCheckedSupplier(retry, () -> externalService.process(100));
        String result = Try.of(cf).onSuccess(t -> System.out.println("Success:" + t)).get();
        System.out.println("Result:" + result);
    }

    @Test
    public void callFailAndRecoveredTest() {
        CheckedFunction0<String> cf = Retry.decorateCheckedSupplier(retry, () -> externalService.process(200));
        String result = Try.of(cf).onSuccess(t -> System.out.println("Success:" + t)).get();
        System.out.println("Result:" + result);
    }

    @Test
    public void allRetryFail() {
        CheckedFunction0<String> cf = Retry.decorateCheckedSupplier(retry, () -> externalService.process(300));

        assertThrows(IllegalStateException.class, () ->
                Try.of(cf)
                        .onSuccess(t -> System.out.println("Success:" + t))
                        .onFailure(t -> System.out.println("Total failure, after all attempts"))
                        .get()
        );

    }

}
