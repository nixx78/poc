package lv.nixx.poc.guice.service;

import com.google.inject.ProvidedBy;

@ProvidedBy(LoggerImpl.class)
public interface ServiceLogger {
	void log(String message);
}
