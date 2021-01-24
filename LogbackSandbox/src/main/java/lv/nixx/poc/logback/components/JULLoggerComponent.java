package lv.nixx.poc.logback.components;

import static lv.nixx.poc.logback.PerfomanceTimeFormater.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.base.Stopwatch;

public class JULLoggerComponent {
	
	private static Logger log = Logger.getLogger("JULLogger");
	
	public static void log(){

		Stopwatch sw = Stopwatch.createStarted();
		log.warning("JULLogger 'WARNING' message");

		System.out.println("log.warning() time [" + format(sw.elapsed(TimeUnit.NANOSECONDS)) + "] milliseconds");

		sw = Stopwatch.createStarted();
		log.finest("JULLogger 'FINEST' message");
		System.out.println("disabled log.finest() time [" +  format(sw.elapsed(TimeUnit.NANOSECONDS)) + "] milliseconds");
	}

}
