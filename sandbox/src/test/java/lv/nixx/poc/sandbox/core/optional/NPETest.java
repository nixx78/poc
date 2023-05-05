package lv.nixx.poc.sandbox.core.optional;

import lombok.Getter;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NPETest {
	
	@Test
	public void classicNPECheck() {
		Outer outer = new Outer();
		outer.nested = new Nested();
		if (outer != null && outer.nested != null && outer.nested.inner != null) {
		    System.out.println(outer.nested.inner.foo);
		}
	}

	@Test
	public void java8NPECheckNullValue() {
		
		String s = Optional.of(new Outer())
	    .map(Outer::getNested)
	    .map(Nested::getInner)
	    .map(Inner::getFoo)
	    .orElse("DEFAULT_VALUE");

		assertEquals("DEFAULT_VALUE", s);
	    
	}

	@Test
	public void java8NPECheckNotNullValue() {
		
		final Outer o = new Outer();
		final Nested n = new Nested();
		final Inner inner = new Inner();
		inner.foo = "FooValue";
		n.inner = inner;
		o.nested = n;
		
		final Optional<String> opt = Optional.of(o)
	    .map(Outer::getNested)
	    .map(Nested::getInner)
	    .map(Inner::getFoo);
		
		assertTrue(opt.isPresent());
		assertEquals("FooValue", opt.get());
	}

	@Getter
	static class Outer {
		Nested nested;
 	}

	@Getter
	static class Nested {
		Inner inner;
	}

	@Getter
	static class Inner {
		String foo;
	}

}
