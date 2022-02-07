package lv.nixx.poc.sandbox.core.Interface;

import lv.nixx.poc.sandbox.interf.ClassAB;
import lv.nixx.poc.sandbox.interf.InterfaceA;
import lv.nixx.poc.sandbox.interf.InterfaceB;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Java8InterfaceTest {
	
	@Test
	public void staticMethodCallFromInterface() {
		assertEquals("static_data:interfaceA", InterfaceA.staticMethodInInterface());
	}
	
	@Test
	public void interfaceInheritance(){
		
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
		assertTrue(ab instanceof ClassAB);

		assertEquals("data:interfaceB", ab.getData());
	}

}
