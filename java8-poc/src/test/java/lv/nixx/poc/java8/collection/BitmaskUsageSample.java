package lv.nixx.poc.java8.collection;

import static org.junit.Assert.*;

import static lv.nixx.poc.java8.collection.BitmaskUsageSample.Permissions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import java.util.stream.Collectors;

import org.junit.Test;

public class BitmaskUsageSample {
	
	@Test
	public void parseValueIntoPermissions() {
		Collection<Permissions> p = getPermissions(1+ 8);
		assertNotNull(p);
		assertEquals(new LinkedHashSet<>(Arrays.asList(WRITE, SEARCH)), p);
	}
	
	@Test
	public void parseAllPossibleValueIntoPermissions() {
		Collection<Permissions> p = getPermissions(1 + 2 + 4 + 8);
		assertNotNull(p);
		assertEquals(new LinkedHashSet<>(Arrays.asList(WRITE, READ, SEARCH, DELETE)), p);
	}
	
	@Test 
	public void parseNullValue() {
		final Collection<Permissions> p = getPermissions(0);
		assertNotNull(p);
		assertTrue(p.isEmpty());
	}
	
	private Collection<Permissions> getPermissions(int value) {
		return Arrays.stream(Permissions.values())
					.filter(p -> (p.bit & value) > 0)
					.collect(Collectors.toSet());
	}

	enum Permissions {
		// Values also can be set as decimal values
		WRITE (0b00001), // 1 
		READ  (0b00010), // 2
		DELETE(0b00100), // 4
		SEARCH(0b01000); // 8

		int bit;

		Permissions(int bit) {
			this.bit = bit;
		}

	}

}
