package lv.nixx.poc.guice.service;

public class LtBankCoreService implements ServiceInterface {

	@Override
	public String processRequest(String req) {
		return "lt" + System.currentTimeMillis() + req;
	}

}
