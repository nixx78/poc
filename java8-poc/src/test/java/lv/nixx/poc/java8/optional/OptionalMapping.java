package lv.nixx.poc.java8.optional;

import static org.junit.Assert.*;

import java.util.Optional;

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
		final Optional<String> filterOpt = s.filter( t-> t.equalsIgnoreCase("xyz"));
		
		assertTrue(filterOpt.isPresent());
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

}
