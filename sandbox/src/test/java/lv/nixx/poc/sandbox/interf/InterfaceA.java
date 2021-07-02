package lv.nixx.poc.sandbox.interf;

public interface InterfaceA {
	
	static String staticMethodInInterface() {
		return "static_data:interfaceA";
	}
	
	default String getData() {
		return "data:interface";
	}
	
	void emptyMethod();
	
	
}
