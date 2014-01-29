package lv.nixx.poc.logback;

import static lv.nixx.poc.logback.PerfomanceTimeFormater.format;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Stopwatch;

public abstract class GenericPerfomanceTest {
	
	protected int iterationsCount() {
		return 10000;
	}
	
	@Test
	public void executePerfomanceTest(){
		Stopwatch sw = Stopwatch.createStarted();
		for(int i = 0; i < iterationsCount(); i++ ) {
			callConcreteLogger();
		}	
		System.out.println("log [" + iterationsCount() + "] time [" + format(sw.elapsed(TimeUnit.NANOSECONDS)) + "] milliseconds");
	}
	
	
	
	abstract protected void callConcreteLogger();

}
