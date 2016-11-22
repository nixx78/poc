package lv.nixx.poc.java8.core.Interface;

import static org.junit.Assert.*;

import org.junit.Test;

public class Java8InterfaceTest {
	
	@Test
	public void staticMethodCallFromInterface() {
		assertEquals("static_data:interfaceA", InterfaceA.staticMethodInInterface());
	}
	
	@Test
	public void interfaceInhiritance(){
		
		InterfaceA ri = new InterfaceA() {

			@Override
			public String getData() { 
				return "data:class";
			}

			@Override
			public void emptyMethod() {
				System.out.println("Empty method called");
			}
		};
		
		// В этом случае, будет вызван метод класса 
		assertEquals("data:class", ri.getData());
	}
	
	@Test
	public void classABTest(){
		ClassAB ab = new ClassAB();
		
		assertTrue(ab instanceof InterfaceA);
		assertTrue(ab instanceof InterfaceB);

		assertEquals("data:interfaceB", ab.getData());
	}

}
