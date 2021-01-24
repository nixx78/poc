package lv.nixx.poc.logback.components;

import static lv.nixx.poc.logback.PerfomanceTimeFormater.format;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

public class Log4jLoggerComponent {

	private static Logger logger = Logger.getLogger("Log4jLogger");

	public static void log(){
		Stopwatch sw = Stopwatch.createStarted();

		logger.debug("Log4j logger message");
		System.out.println("log4j.warning() time [" + format(sw.elapsed(TimeUnit.NANOSECONDS)) + "] milliseconds");
	}
	
}
