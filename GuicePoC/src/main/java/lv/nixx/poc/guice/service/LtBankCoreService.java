package lv.nixx.poc.guice.service;

import javax.inject.Singleton;

@Singleton
public class LtBankCoreService implements ServiceInterface {

	@Override
	public String processRequest(String req) {
		return "lt" + System.currentTimeMillis() + req;
	}

}
