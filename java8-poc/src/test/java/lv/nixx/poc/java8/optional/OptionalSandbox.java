package lv.nixx.poc.java8.optional;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

public class OptionalSandbox {
	
	@Test(expected = NoSuchElementException.class)
	public void optionalWithNull() {
		Optional<String> o = Optional.ofNullable(null);

		assertFalse(o.isPresent());
		assertNull(o.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void emptyOptional() {
		Optional<String> o = Optional.empty();
		
		assertFalse(o.isPresent());
		assertNull(o.get());
	}
	
	@Test
	public void optionalWithValue() {
		Optional<String> o = Optional.of("StringValue");
		assertTrue(o.isPresent());

		Set<String> s = new HashSet<>();
		o.ifPresent(s::add);

		System.out.println(s);
	}
	

}
