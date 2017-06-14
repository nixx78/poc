package lv.nixx.poc.guice.service;

import javax.inject.Inject;

import com.google.inject.Provider;

public class LvServiceProvider implements Provider<ServiceInterface> {
	
	private final ServiceLogger logger;
	
	@Inject
	public LvServiceProvider(ServiceLogger logger) {
		this.logger = logger;
	}
	
	@Override
	public ServiceInterface get() {
		return new LvBankCoreService(logger);
	}

}
