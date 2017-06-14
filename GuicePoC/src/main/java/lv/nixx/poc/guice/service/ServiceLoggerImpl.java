package lv.nixx.poc.guice.service;

public class ServiceLoggerImpl implements ServiceLogger {

	@Override
	public void log(String message) {
		System.out.println("log:" + message);
	} 

}
