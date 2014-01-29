package lv.nixx.poc.logback.mdc;

import org.junit.*;

public class TrivialServiceTest {
	
	@BeforeClass
	public static void init(){
		System.setProperty("logback.configurationFile", "logback_mdc.xml");
	}

	@Test
	public void simpleServiceTest(){
		Service service = new Service();
		service.process("userId1","request");
	}

	
}
