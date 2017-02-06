package lv.nixx.poc.sandbox.concurrency;

import static java.util.concurrent.TimeUnit.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.*;
import org.junit.Test;

public class ConcurrencySnippets {

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
	public void scheduledExecutrorTest() throws InterruptedException, ExecutionException {

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

		public Printer(int seqLength) {
			this.seqLength = seqLength;
		}

		@Override
		public String call() throws Exception {
			String s = Thread.currentThread().getName() + ":" + new String(new char[seqLength]).replace("\0", "*");
			System.out.println(s);
			return s;
		}
	}

}
