package lv.nixx.poc.aop;

import static org.junit.Assert.*;

import org.junit.Test;

import lv.nixx.poc.annotation.DrinkOrderProcessor;
import lv.nixx.poc.annotation.MealOrderProcessor;
import lv.nixx.poc.annotation.OrderProcessor;
import lv.nixx.poc.annotation.OrderProcessorFactory;

public class OrderFactoryTest {
	
	@Test
	public void processorCreationTest() {

		OrderProcessorFactory f = new OrderProcessorFactory();
		
		OrderProcessor p = f.getProcessor(MealOrderProcessor.class);
		assertNotNull(p);
		assertTrue(p instanceof MealOrderProcessor);

		p = f.getProcessor(DrinkOrderProcessor.class);
		assertNotNull(p);
		assertTrue(p instanceof DrinkOrderProcessor);
		
		f.getProcessor(DrinkOrderProcessor.class);
		f.getProcessor(DrinkOrderProcessor.class);

		assertEquals(3, f.getFastProcessing());
		assertEquals(1, f.getMediumProcessing());		
	}
	

}

