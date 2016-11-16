package lv.nixx.poc.aspect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfiguration.class)
public class BussinessServiceWithAspectTest {
	
	@Autowired
	BussinessService service;
	
	@Test
	public void process2Call() {
		final String r = service.process2("string", 777);
		System.out.println(r);
	}

	@Test
	public void process1Call() {
		final String r = service.process1();
		System.out.println(r);
	}

}
