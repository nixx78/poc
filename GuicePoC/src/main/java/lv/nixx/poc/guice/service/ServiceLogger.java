package lv.nixx.poc.guice.service;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceLoggerImpl.class)
public interface ServiceLogger {
	void log(String message);
}
