package lv.nixx.poc.aspect;

import org.springframework.stereotype.Component;

@Component
public class BussinessService {
	
	public String process1() {
		return "process1:" + System.currentTimeMillis();
	}
	
	public String process2(String s, int i) {
		return "process2:" + s + ":"  + i + ":" + System.currentTimeMillis();
	}

}
