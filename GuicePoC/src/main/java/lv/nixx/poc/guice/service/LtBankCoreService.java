package lv.nixx.poc.guice.service;

import javax.inject.Inject;

public class LtBankCoreService implements ServiceInterface {
	
	AuditService audit;
	
	@Inject
	public LtBankCoreService(AuditService audit) {
		this.audit = audit;
	}
	
	@Override
	public String processRequest(String req) {
		audit.log("LT Code call");
		return "lt" + System.currentTimeMillis() + req;
	}


}
