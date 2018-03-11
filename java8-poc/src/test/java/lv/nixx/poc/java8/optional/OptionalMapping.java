package lv.nixx.poc.java8.optional;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

public class OptionalMapping {

	@Test
	public void notNullableObjectOptionalTest() {
		final Optional<String> opt = Optional.of("");

		// t - instance of A
		final Optional<B> mappedOpt = opt.map(t -> new B(t));

		assertTrue(mappedOpt.isPresent());
		mappedOpt.ifPresent(t -> System.out.println(t.getValue()));
	}

	@Test
	public void nullableObjectOptionalTest() {

		final Optional<String> opt = Optional.ofNullable(null);

		// t - instance of A
		final Optional<B> mappedOpt = opt.map(t -> new B(t));

		assertFalse(mappedOpt.isPresent());

		mappedOpt.ifPresent(t -> System.out.println(t.getValue()));
	}

	@Test
	public void orElseTest() {

		String s = Optional.ofNullable((String) null).orElse("else.value");
		assertEquals("else.value", s);

		s = Optional.ofNullable("curr.value").orElse("else.value");
		assertEquals("curr.value", s);

	}

	@Test
	public void orElseGetTest() {
		String s = Optional.ofNullable((String) null).orElseGet(this::getElseValue);
		assertEquals("else.value", s);
	}

	@Test
	public void optionalFilter() {

		Optional<String> s = Optional.of("xyz");
		final Optional<String> filterOpt = s.filter(t -> t.equalsIgnoreCase("xyz"));

		assertTrue(filterOpt.isPresent());
	}

	@Test
	public void optionalMap() {

		final Optional<Integer> optNotPresent = Optional.of(new Person("name.value", new Address("street.value", null)))
				.map(Person::getAddress).map(Address::getHouseNum);

		assertFalse(optNotPresent.isPresent());

		final Optional<Integer> optPresent = Optional.of(new Person("name.value", new Address("street.value", 100)))
				.map(Person::getAddress).map(Address::getHouseNum);

		assertTrue(optPresent.isPresent());
		assertEquals((int) 100, (int) optPresent.get());

		Collection<Person> persons = Arrays.asList(new Person("name.value0", new Address("street.value0", 100)),
				new Person("name.value1", new Address("street.value1", null)),
				new Person("name.value2", new Address("street.value2", 200)),
				new Person("name.value3", new Address("street.value3", 300)));

		final List<Integer> collect = persons.stream()
				.map(Person::getAddress)
				.map(Address::getHouseNum)
				.filter(t -> t != null)
				.filter(t -> t > 200)
				.collect(Collectors.toList());

		collect.forEach(System.out::println);

	}

	private String getElseValue() {
		return "else.value";
	}

	class B {

		String aVal;

		public B(String v) {
			this.aVal = v;
		}

		String getValue() {
			return aVal + "returnB";
		}
	}

	class Person {
		String name;
		Address address;

		Person(String name, Address address) {
			this.name = name;
			this.address = address;
		}

		String getName() {
			return name;
		}

		Address getAddress() {
			return address;
		}

	}

	class Address {
		String street;
		Integer houseNum;

		Address(String street, Integer houseNum) {
			this.street = street;
			this.houseNum = houseNum;
		}

		String getStreet() {
			return street;
		}

		Integer getHouseNum() {
			return houseNum;
		}

	}

}
