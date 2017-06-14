package lv.nixx.poc.guice;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import lv.nixx.poc.guice.service.LtBankCoreService;
import lv.nixx.poc.guice.service.LvBankCoreService;
import lv.nixx.poc.guice.service.ServiceInterface;

public class CoreBankConfig extends AbstractModule {

	@Override
	protected void configure() {
	}
	
	@Provides @LV @Singleton
	public ServiceInterface ltCoreService() {
		return new LtBankCoreService();
	}

	@Provides @LT @Singleton
	public ServiceInterface lvCoreService() {
		return new LvBankCoreService();
	}
	

}
