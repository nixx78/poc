package lv.nixx.poc.logback.components;

import java.util.concurrent.TimeUnit;

import static lv.nixx.poc.logback.PerfomanceTimeFormater.format;

import org.slf4j.*;

import com.google.common.base.Stopwatch;

public class SLF4JLoggerComponent {

	static Logger logger = LoggerFactory.getLogger("SLF4Llogger");
	
	public static void log(){

		Stopwatch sw = Stopwatch.createStarted();
		logger.debug("SLF4L logger message");
		System.out.println("slf4j.debug() time [" + format(sw.elapsed(TimeUnit.NANOSECONDS)) + "] milliseconds");
	}
	
}


