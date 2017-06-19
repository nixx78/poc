package lv.nixx.poc.guice;

import com.google.inject.AbstractModule;

import lv.nixx.poc.guice.annotations.LT;
import lv.nixx.poc.guice.annotations.LV;
import lv.nixx.poc.guice.service.LvServiceProvider;
import lv.nixx.poc.guice.service.ServiceInterface;

public class CoreBankConfig extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(ServiceInterface.class).annotatedWith(LV.class).toProvider(LvServiceProvider.class);
		bind(ServiceInterface.class).annotatedWith(LT.class).toProvider(LtServiceProvider.class);
	}

}
