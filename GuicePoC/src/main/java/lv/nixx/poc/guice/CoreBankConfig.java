package lv.nixx.poc.guice;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class CoreBankConfig extends AbstractModule {

	@Override
	protected void configure() {
	}
	
	@Provides @LV @Singleton
	public Service ltCoreService() {
		return new LtBankCoreService();
	}

	@Provides @LT @Singleton
	public Service lvCoreService() {
		return new LvBankCoreService();
	}
	

}
