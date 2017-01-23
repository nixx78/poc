package lv.nixx.poc.delayedqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

public class DelayQueueTest {

	public static void main(String[] args) throws InterruptedException {

		final BlockingQueue<Email> queue = new DelayQueue<>();

		new DelayQueueProducer(queue).start();
		new DelayQueueConsumer("Consumer Thread-1", queue).start();

	}
	
}