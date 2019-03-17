package lv.nixx.poc.sandbox.concurrency;

import static java.util.concurrent.TimeUnit.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import org.junit.Test;

public class ConcurrencySnippets {


    @Test
    public void cyclicBarrierSample() {

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> System.out.println("Barrier done!"));

        IntStream.of(1000, 100, 200, 300, 500).mapToObj(t -> (Runnable) () -> {
                    final String tn = Thread.currentThread().getName();
                    try {
                        cyclicBarrier.await();
                        System.out.println("Thread [" + tn + "] will sleep [" + t + "]");
                        Thread.sleep(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("Thread [" + tn + "] done");
                }
        ).map(Thread::new)
                .forEach(Thread::start);
    }


    @Test
    public void countDownLatchSample() throws InterruptedException {

        CountDownLatch cd = new CountDownLatch(3);

        IntStream.of(1000, 100, 200).mapToObj(t -> (Runnable) () -> {
                    final String tn = Thread.currentThread().getName();
                    try {
                        System.out.println("Thread [" + tn + "] will sleep [" + t + "]");
                        Thread.sleep(t);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cd.countDown();
                    System.out.println("Thread [" + tn + "] done");
                }
        ).map(Thread::new)
                .forEach(Thread::start);

        System.out.println("Waiting for all treads..");

        cd.await(2, TimeUnit.SECONDS);
        System.out.println("All threads are ready");

    }

    @Test
    public void executorSampleWithAwaitTermination() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            System.out.println("HelloStarted");
            String threadName = Thread.currentThread().getName();
            try {
                MILLISECONDS.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Hello " + threadName);
        });
        executor.awaitTermination(6, SECONDS);
    }

    @Test
    public void callableSample() throws Exception {

        ExecutorService exec = Executors.newFixedThreadPool(10);

        List<Callable<String>> lst = Arrays.asList(
                new Printer(1),
                new Printer(2),
                new Printer(3),
                new Printer(4));

        List<Future<String>> futures = exec.invokeAll(lst);

        System.out.println("=== Features: order is equals to callable list order ===");
        for (Future<String> f : futures) {
            if (f.isDone()) {
                System.out.println(f.get(1, SECONDS));
            }
        }

    }

    @Test
    public void scheduledExecutrorTest() throws InterruptedException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        executor.scheduleAtFixedRate(task, 0, 1, SECONDS);
        executor.awaitTermination(5, SECONDS);
    }

    @Test
    public void semaphoreUsageSample() {
        Semaphore s = new Semaphore(3);

        assertTrue(s.tryAcquire());
        assertTrue(s.tryAcquire());
        assertTrue(s.tryAcquire());

        assertFalse(s.tryAcquire());
        assertFalse(s.tryAcquire());

        s.release();
        assertFalse(s.tryAcquire(3));
        assertTrue(s.tryAcquire(1));
    }

    class Printer implements Callable<String> {
        int seqLength;

        Printer(int seqLength) {
            this.seqLength = seqLength;
        }

        @Override
        public String call() {
            String s = Thread.currentThread().getName() + ":" + new String(new char[seqLength]).replace("\0", "*");
            System.out.println(s);
            return s;
        }
    }

}
