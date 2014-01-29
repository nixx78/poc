package lv.nixx.poc.logback.mdc;

public class ThreadLocalHolder {
	
	static ThreadLocal<String> t = new ThreadLocal<String>();

	public static void storeValue(String value){
		t.set(value);
	}
	
	public static String getValue(){
		return t.get();
	}

}
