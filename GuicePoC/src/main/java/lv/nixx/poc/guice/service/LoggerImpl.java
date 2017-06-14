package lv.nixx.poc.guice.service;

public class LoggerImpl implements ServiceLogger {

	@Override
	public void log(String message) {
		System.out.println("log:" + message);
	} 

}
