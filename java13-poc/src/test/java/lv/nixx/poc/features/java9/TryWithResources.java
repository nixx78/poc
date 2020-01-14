package lv.nixx.poc.features.java9;

import java.io.Closeable;
import java.io.IOException;

import org.junit.Test;

public class TryWithResources {

	@Test
	public void java17Sample() throws IOException {
		try (Closeable c = new CloseableImpl()) {
			System.out.println("Process");
		}
	}

	@Test
	public void java19Sample() throws IOException {
		Closeable c = new CloseableImpl();
		try (c) {
			System.out.println("Process");
		}
	}

	class CloseableImpl implements Closeable {

		@Override
		public void close() throws IOException {
			System.out.println("Closeable called");
		}
		
	}
}
