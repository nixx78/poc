package lv.nixx.poc.guice.congif;

import javax.inject.Named;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import lv.nixx.poc.guice.annotations.LT;
import lv.nixx.poc.guice.annotations.LV;
import lv.nixx.poc.guice.provider.LtServiceProvider;
import lv.nixx.poc.guice.provider.LvServiceProvider;
import lv.nixx.poc.guice.service.AuditService;
import lv.nixx.poc.guice.service.ServiceInterface;
import lv.nixx.poc.guice.service.SysoutAuditService;

public class CoreBankConfig extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(ServiceInterface.class).annotatedWith(LV.class).toProvider(LvServiceProvider.class);
		bind(ServiceInterface.class).annotatedWith(LT.class).toProvider(LtServiceProvider.class);
	}
	
	@Provides @Named("lvAudit")
	public AuditService lvAudit() {
		return new SysoutAuditService("LVAuditService");
	}
	
	@Provides @Named("ltAudit")
	public AuditService ltAudit() {
		return new SysoutAuditService("LTAuditService");
	}


}
