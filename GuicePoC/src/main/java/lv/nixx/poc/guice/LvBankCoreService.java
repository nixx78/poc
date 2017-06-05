package lv.nixx.poc.guice;

import javax.inject.Singleton;

@Singleton
public class LvBankCoreService implements Service {

	@Override
	public String processRequest(String req) {
		return "lv" + System.currentTimeMillis() + req;
	}

}
