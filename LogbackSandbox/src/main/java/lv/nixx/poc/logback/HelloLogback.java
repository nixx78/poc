package lv.nixx.poc.logback;

import org.slf4j.*;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class HelloLogback {

	public static void main(String[] args) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);

		Logger logger = LoggerFactory
				.getLogger("chapters.introduction.HelloWorld1");
		logger.debug("Hello world.");

		logger.debug("param1 [{}[ param2 [{}] param3 [{}]", "1", "2", "3");
		logger.debug("param1 [{}[ param2 [{}] param3 [{}]", new Object[] { "1", "2", new HolderSample() });

		Marker marker = MarkerFactory.getMarker("my marker");
		logger.debug(marker, "message with marker");
	}

	static class HolderSample {
		@Override
		public String toString() {
			return "value";
		}
	}

}
