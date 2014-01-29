package lv.nixx.poc.logback;

import lv.nixx.poc.logback.components.Log4jLoggerComponent;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class Log4jPerfomanceTest extends GenericPerfomanceTest{
	
	static Logger logger = Logger.getLogger("Log4lLogger");
	
	@BeforeClass
	public static void initLogger(){
		BasicConfigurator.configure();
		Logger rootLogger = Logger.getRootLogger();
	}
	
	@Test
	@Ignore
	public void testLog4jPerfomance(){
		Log4jLoggerComponent.log();
	}

	@Override
	protected void callConcreteLogger() {
		logger.debug("Log4j logger message");
	}
}
