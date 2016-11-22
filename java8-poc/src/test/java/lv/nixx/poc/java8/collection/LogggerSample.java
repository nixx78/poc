package lv.nixx.poc.java8.collection;

import java.util.function.Supplier;

import org.junit.Test;

public class LogggerSample {

	@Test
	public void testDebug() {
		
		Logger log = new Logger();
		
		log.debug(()->"Result:" + getLogData());
		
	}
	
	private String getLogData() {
		return "Long Calculated log data";
	}

	class Logger {
		
		private void debug(Supplier<String> message) {
			if (isDebugEnabled()){
				System.out.println(message.get());
			}
		}

		private boolean isDebugEnabled() {
			return false;
		}
		
	}
	
	
}
