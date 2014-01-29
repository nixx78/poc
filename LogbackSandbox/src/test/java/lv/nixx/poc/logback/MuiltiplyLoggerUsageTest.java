package lv.nixx.poc.logback;

import lv.nixx.poc.logback.components.*;

import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class MuiltiplyLoggerUsageTest {

	@Test
	public void multiplyLoggerTest() {
		SLF4JBridgeHandler.removeHandlersForRootLogger(); // (since SLF4J 1.6.5)
		SLF4JBridgeHandler.install();

		Log4jLoggerComponent.log();
		JULLoggerComponent.log();
		SLF4JLoggerComponent.log();
	}

}
