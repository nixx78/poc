package lv.nixx.poc.java8.completefuture;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class CompletableFutureSandbox {
	
	final Map<String, String> users = new ConcurrentHashMap<>();

	@Before
	public void init() {
		users.put("id1", "id1:name1");
		users.put("id2", "id2:name2");
		users.put("id3", "id3:name3");
		users.put("id4", "id4:name4");
		users.put("id5", "id5:name5");
		users.put("id6", "id6:name6");
	}

	@Test
	public void helloFutureTest() throws Exception {

		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello").thenApply(s -> s + " World");

		System.out.println(completableFuture.get());
	}

	@Test
	public void createCompleteFuture() throws Exception {

		String userId = "id1";

		final CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> getUserInfo(userId))
				.thenApply(this::convert)
				.thenApply(this::convertResult)
				.exceptionally(this::processException);

		assertEquals("id1:name1", cf.get());
	}
	
	@Test
	public void combineFuturesTest() throws Exception {
		
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
		    .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
		 
		assertEquals("Hello World", completableFuture.get());
	}

	@Test(expected = ExecutionException.class)
	public void completeFutureWithException() throws Exception {
		CompletableFuture<String> thenApply = CompletableFuture.supplyAsync(() -> getUserInfo("ERROR"))
		.thenApply(this::convert)
		.thenApply(this::convertResult);

		thenApply.get();
	}

	private String processException(Throwable e) {
		System.err.println(e);
		return null;
	}

	@Test
	public void allOfFutures() {
		Collection<CompletableFuture<User>> c = new ArrayList<>();
		final ExecutorService tp = Executors.newFixedThreadPool(3);

		for (String id : users.keySet()) {
			final CompletableFuture<User> cf = CompletableFuture.supplyAsync(() -> getUserInfo(id), tp).thenApply(this::convert);
			c.add(cf);
		}

		List<User> users = CompletableFuture.allOf(c.toArray(new CompletableFuture[c.size()]))
				.thenApply(v -> c.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList())).thenApply(t -> {
					
			System.out.println("====== All is done =======");
			return t;
		}).join();

		users.forEach(System.out::println);
	}

	private String getUserInfo(String id) {
		try {

			if (id.equals("ERROR")) {
				throw new IllegalArgumentException("GetUserInfo error");
			}

			final int st = ThreadLocalRandom.current().nextInt(50, 2000 + 1);
			System.out.println("Thread [" + Thread.currentThread().getName() + "] Call: send request id [" + id + "] and wait [" + st + "]");
			TimeUnit.MILLISECONDS.sleep(st);

		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return users.get(id);
	}

	private String convertResult(User res) {
		System.out.println("Thread [" + Thread.currentThread().getName() + "] Result: " + res);

		return res.id + ":" + res.name;
	}

	private User convert(String body) {
		StringTokenizer st = new StringTokenizer(body, ":");
		User u = new User();
		final String id = u.id = st.nextToken();
		u.name = st.nextToken();

		System.out.println("Thread [" + Thread.currentThread().getName() + "] Id [" + id + "] convert success");
		return u;
	}

	class User {
		String id;
		String name;

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}
	}

}
