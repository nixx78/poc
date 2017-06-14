package lv.nixx.poc.guice.service;

import javax.inject.Singleton;

@Singleton
public class LvBankCoreService implements ServiceInterface {

	@Override
	public String processRequest(String req) {
		return "lv" + System.currentTimeMillis() + req;
	}

}
