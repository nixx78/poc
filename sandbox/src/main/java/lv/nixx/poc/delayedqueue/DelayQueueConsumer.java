package lv.nixx.poc.delayedqueue;

import java.util.concurrent.BlockingQueue;

public class DelayQueueConsumer {

	private String name;
	private final BlockingQueue<Email> queue;

	public DelayQueueConsumer(String name, BlockingQueue<Email> queue) {
		this.name = name;
		this.queue = queue;
	}

	private Thread consumerThread = new Thread(new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					Email email = queue.take();
					System.out.printf("[%s] - Sending mail when delay is over = %s%n", name, email);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	});

	public void start() {
		this.consumerThread.setName(name);
		this.consumerThread.start();
	}

}