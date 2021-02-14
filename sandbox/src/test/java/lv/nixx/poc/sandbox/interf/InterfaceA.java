package lv.nixx.poc.sandbox.interf;

public interface InterfaceA {
	
	public static String staticMethodInInterface() {
		return "static_data:interfaceA";
	}
	
	public default String getData() {
		return "data:interface";
	}
	
	public void emptyMethod();
	
	
}
