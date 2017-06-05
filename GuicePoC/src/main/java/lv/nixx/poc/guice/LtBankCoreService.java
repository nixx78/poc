package lv.nixx.poc.guice;

import javax.inject.Singleton;

@Singleton
public class LtBankCoreService implements Service {

	@Override
	public String processRequest(String req) {
		return "lt" + System.currentTimeMillis() + req;
	}

}
