package lv.nixx.poc.java8.core.reference;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

public class FunctionSandbox {
	
	
	@Test
	public void supplierUseSample() {
		Person p1 = new Person("name1", "surname1", new Date());
		process(p1::getName);
	}

	@Test
	public void functionUseSample() {
		Person p1 = new Person("name1", "surname1", new Date());
		process(p1, Person::getSurname);
	}

	private void process(Supplier<String> s) {
		System.out.println(s.get() + ":process");
	}
	
	private void process(Person p, Function<Person, String> f) {
		System.out.println(f.apply(p) + ":process");
	}
	
	@Test
	public void methodRefSample() {
		MyClass mc = new MyClass();
		compare(mc::plus);
	}
	
	private void compare(MyInterface s) {
		System.out.println(s.plus(10,  20));
	}
	
	interface MyInterface {
		int plus(int a, int b);
	}
	
	class MyClass implements MyInterface {

		@Override
		public int plus(int a, int b) {
			return a + b;
		}
		
	}
	
	@Data
	@AllArgsConstructor
	class Person {
		private String name;
		private String surname;
		private Date birhtDate;
	}

}
