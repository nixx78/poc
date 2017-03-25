package lv.nixx.poc.java8.npe;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

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



	class Outer {
		Nested nested;

		Nested getNested() {
			return nested;
		}
	}

	class Nested {
		Inner inner;

		Inner getInner() {
			return inner;
		}
	}

	class Inner {
		String foo;

		String getFoo() {
			return foo;
		}
	}

}
