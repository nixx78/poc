package lv.nixx.poc.java8.collection.txn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Test;

public class ThreadLocalLambda {
	
	ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("ddMMyyyy"));
	
	@Test
	public void test() throws Exception {
		
		Runnable r1 = () -> {
			try {
				System.out.println(Thread.currentThread().getName() + ":" + df.get().parse("01122016"));
				synchronized (this) {
					wait(200);
				}
				System.out.println("R1 End");
			} catch (Exception e) {
				e.printStackTrace();
		}};

		Runnable r2 = () -> {
			try {
				System.out.println(Thread.currentThread().getName() + ":" + df.get().parse("02122016"));
			} catch (Exception e) {
				e.printStackTrace();
		}};

		Thread[] ta = {new Thread(r1), new Thread(r2)};
		Arrays.stream(ta).forEach(t -> t.start());

		Arrays.stream(ta).forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

}
