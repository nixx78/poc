package lv.nixx.poc.resilience.retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class RetrySandbox {

    @Test
    public void sandbox() {

        RetryConfig c = RetryConfig.custom()
                .maxAttempts(10)
                .waitDuration(Duration.ofSeconds(5L))
                .retryOnException(e -> e instanceof IllegalStateException)
                .retryOnResult( e-> e.equals("Fail"))
                .build();

        final Retry retry = Retry.of("retry1", c);

        final Retry.EventPublisher ep = retry.getEventPublisher();

        ep.onRetry(e -> {
            System.out.println("Retry event, type:" + e.getEventType() + ", try:" + e.getNumberOfRetryAttempts() + " error: " + e.getLastThrowable());
        });

        ep.onSuccess(e -> {
            System.out.println("Retry event, type:" + e.getEventType() + ", try:" + e.getNumberOfRetryAttempts() + "  last Throwable: " + e.getLastThrowable());
        });

        Service service = new Service();

        final String result = retry.executeSupplier(() -> service.method("Param1"));
        System.out.println("Result:" + result);
    }

    class Service {
        int i = 0;

        String method(String param) {
            System.out.println("Method call, param:" + param);
            try {
                 if (i <= 3) {
                    throw new IllegalStateException("Exception in service");
                }

                if (i == 4) {
                    return "Fail";
                }
                return "Success:" + param;
            } finally {
                i++;
            }
        }
    }


}
