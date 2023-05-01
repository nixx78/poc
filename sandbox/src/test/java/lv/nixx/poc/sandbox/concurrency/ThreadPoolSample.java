package lv.nixx.poc.sandbox.concurrency;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

//TODO https://engineering.zalando.com/posts/2019/04/how-to-set-an-ideal-thread-pool-size.html

class ThreadPoolSample {

    @Test
    void runBatchInThreadPool() {

        int numOfCores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of cores: " + numOfCores);

        Collection<Request> requestsToExecute = List.of(
                new Request("request1", 0L),
                new Request("request2", 100L),
                new Request("request3", 1000L),
                new Request("request4", 10L),
                new Request("request5", 30L),
                new Request("request6", 50L)
        );
        ExecutorService pool = Executors.newFixedThreadPool(3);

        List<Future<String>> futures = requestsToExecute.stream()
                .map(pool::submit)
                .toList();

        Collection<String> responses = new ArrayList<>(requestsToExecute.size());
        futures.forEach(
                f -> {
                    try {
                        responses.add(f.get(1000L, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException | RuntimeException | ExecutionException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        System.out.println("Batch response:");
        responses.forEach(System.out::println);
    }

    @Test
    void invokeAllSample() throws InterruptedException {

        Collection<Request> requestsToExecute = List.of(
                new Request("request1", 0L),
                new Request("request2", 100L),
                new Request("request3", 1000L),
                new Request("request4", 10L),
                new Request("request5", 30L),
                new Request("request6", 50L)
        );
        ExecutorService pool = Executors.newFixedThreadPool(3);

        List<Future<String>> futures = pool.invokeAll(requestsToExecute);

        Collection<String> responses = new ArrayList<>(requestsToExecute.size());
        futures.forEach(
                f -> {
                    try {
                        responses.add(f.get(10L, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException | RuntimeException | ExecutionException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        System.out.println("Batch response:");
        responses.forEach(System.out::println);
    }

    @RequiredArgsConstructor
    static class Request implements Callable<String> {

        private final String request;
        private final long delay;

        @Override
        public String call() throws Exception {
            long stTime = System.currentTimeMillis();
            TimeUnit.MILLISECONDS.sleep(delay);
            String s = "[" + request + "] processed by:" + Thread.currentThread().getName() + " time:" + (System.currentTimeMillis() - stTime);
            System.out.println("Call: " + s);
            return s;
        }
    }


}
