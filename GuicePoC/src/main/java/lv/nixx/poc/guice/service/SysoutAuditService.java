package lv.nixx.poc.guice.service;

public class SysoutAuditService implements AuditService {

	public void log(String message) {
		System.out.println("Audit:" + message);
	}
}
