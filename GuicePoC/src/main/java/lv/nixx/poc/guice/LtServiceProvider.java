package lv.nixx.poc.guice;

import javax.inject.Inject;
import javax.inject.Provider;

import lv.nixx.poc.guice.service.AuditService;
import lv.nixx.poc.guice.service.LtBankCoreService;
import lv.nixx.poc.guice.service.ServiceInterface;

public class LtServiceProvider implements Provider<ServiceInterface> {
	
	@Inject
	AuditService auditService;

	@Override
	public ServiceInterface get() {
		return new LtBankCoreService(auditService);
	}

}
