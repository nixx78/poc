package lv.nixx.poc.guice.service;

import com.google.inject.ImplementedBy;

@ImplementedBy(SysoutAuditService.class)
public interface AuditService {
	public void log(String message);
}
