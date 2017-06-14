package lv.nixx.poc.guice;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import lv.nixx.poc.guice.annotations.LT;
import lv.nixx.poc.guice.annotations.LV;
import lv.nixx.poc.guice.service.LtBankCoreService;
import lv.nixx.poc.guice.service.LvServiceProvider;
import lv.nixx.poc.guice.service.ServiceInterface;

public class CoreBankConfig extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(ServiceInterface.class).annotatedWith(LV.class).toProvider(LvServiceProvider.class);
	}
	
	@Provides 
	@LT 
	@Singleton
	public ServiceInterface lvCoreService() {
		return new LtBankCoreService();
	}
	

}
