package lv.nixx.poc.guice.service;

public class LvBankCoreService implements ServiceInterface {
	
	public ServiceLogger logger;
	
	public LvBankCoreService(ServiceLogger logger) {
		this.logger = logger;
	}

	@Override
	public String processRequest(String req) {
		logger.log("Request: " + req + ": come");
		return "lv" + System.currentTimeMillis() + req;
	}

}
