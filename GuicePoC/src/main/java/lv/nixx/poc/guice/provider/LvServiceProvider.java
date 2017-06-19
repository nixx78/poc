package lv.nixx.poc.guice.provider;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.inject.Provider;

import lv.nixx.poc.guice.service.AuditService;
import lv.nixx.poc.guice.service.LvBankCoreService;
import lv.nixx.poc.guice.service.ServiceInterface;
import lv.nixx.poc.guice.service.ServiceLogger;

public class LvServiceProvider implements Provider<ServiceInterface> {

	private final ServiceLogger logger;

	@Inject
	@Named("ltAudit")
	AuditService auditService;

	@Inject
	public LvServiceProvider(ServiceLogger logger) {
		this.logger = logger;
	}

	@Override
	public ServiceInterface get() {
		return new LvBankCoreService(logger, auditService);
	}

}
