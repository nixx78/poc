package lv.nixx.poc.java8.core.reference;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

public class MethodReference {
	
	@Test
	public void test() {
		Person p1 = new Person("name1", "surname1", new Date());
		Person p2 = new Person("name2", "surname2", new Date());
		
		List<Wrapper> methods = Arrays.asList(
				new Wrapper(Person::getName, "name"), 
				new Wrapper(Person::getSurname, "surname"), 
				new Wrapper(Person::getBirthdate, "birthdate")
				);
		
		execute(p1, p2, methods);
		
	}
	
	private void execute(Person p1, Person p2, List<Wrapper> lst ) {
		for (Wrapper f : lst) {
			final Object r1 = f.funct.apply(p1);
			final Object r2 = f.funct.apply(p2);
			System.out.println(f.name + "::" + r1 + ":" + r2);
		}
	}
	
	class Wrapper {
		
		Function<Person, Object> funct;
		String name;
		
		Wrapper(Function<Person, Object> c, String  name) {
			this.funct = c;
			this.name = name;
		}
		
	}
	

	
	class Person {
		private String name;
		private String surname;
		private Date birhtDate;
		
		public Person(String name, String surname, Date birhtDate) {
			this.name = name;
			this.surname = surname;
			this.birhtDate = birhtDate;
		}

		public String getName() {
			return name;
		}

		public String getSurname() {
			return surname;
		}

		public Date getBirthdate() {
			return birhtDate;
		}

	}

}
