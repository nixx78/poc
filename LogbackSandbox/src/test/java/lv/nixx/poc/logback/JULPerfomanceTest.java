package lv.nixx.poc.logback;

import lv.nixx.poc.logback.components.JULLoggerComponent;

import java.util.logging.*;

import org.junit.*;

public class JULPerfomanceTest extends GenericPerfomanceTest {

	static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@BeforeClass
	public static void configureJULLogger() throws Exception {
		SimpleFormatter simpleFormatter = new SimpleFormatter();

		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(simpleFormatter);
		log.addHandler(consoleHandler);
		
		FileHandler fileHandler = new FileHandler("./log/jul.log");
		fileHandler.setFormatter(simpleFormatter);
		log.addHandler(fileHandler);
		
		log.setLevel(Level.ALL);
		log.info("initializing - trying to load configuration file ...");
	}
	
	@Test
	@Ignore
	public void testJULPurePerfomance() {
		JULLoggerComponent.log();
	}

	@Override
	protected void callConcreteLogger() {
		log.warning("JULLogger 'WARNING' message");
	}
	
	
}
