package lv.nixx.poc.guice.service;

public class LvBankCoreService implements ServiceInterface {
	
	public ServiceLogger logger;
	public AuditService auditService;
	
	public LvBankCoreService(ServiceLogger logger, AuditService auditService) {
		this.logger = logger;
		this.auditService = auditService;
	}

	@Override
	public String processRequest(String req) {
		logger.log("Request: " + req + ": come");
		auditService.log("lv request");
		return "lv" + System.currentTimeMillis() + req;
	}

}
