package lv.nixx.poc.java8.core.Interface;

public interface InterfaceA {
	
	public static String staticMethodInInterface() {
		return "static_data:interfaceA";
	}
	
	public default String getData() { 
		return "data:interface";
	}
	
	public void emptyMethod();
	
	
}
