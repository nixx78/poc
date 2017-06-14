package lv.nixx.poc.guice.service;

import javax.inject.Singleton;

import com.google.inject.Provider;

@Singleton
public class LoggerImpl implements Provider<ServiceLogger> {

	@Override
	public ServiceLogger get() {
		return null;
	}

}
