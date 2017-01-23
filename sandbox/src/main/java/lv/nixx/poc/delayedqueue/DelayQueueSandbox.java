package lv.nixx.poc.delayedqueue;

import java.util.Arrays;
import java.util.concurrent.DelayQueue;
import org.junit.Test;

public class DelayQueueSandbox {

	@Test
	public void test() throws InterruptedException {

		Email e2 = new Email("receiver2", "Body2", 1500);
		Email e3 = new Email("receiver3", "Body3", 1000);
		Email e1 = new Email("receiver1", "Body1", 500);

		DelayQueue<Email> q = new DelayQueue<>(Arrays.asList(e1, e2, e3));
		
		System.out.println("DelayQueue filled");

		System.out.println(q.take());
		System.out.println(q.take());
		System.out.println(q.take());
	}

}
