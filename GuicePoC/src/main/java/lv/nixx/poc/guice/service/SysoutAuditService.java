package lv.nixx.poc.guice.service;

public class SysoutAuditService implements AuditService {

	private final String name;

	public SysoutAuditService(String name) {
		this.name = name;
	}

	public void log(String message) {
		System.out.println(name + ":audit:" + message);
	}
}
