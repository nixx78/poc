package lv.nixx.poc.sandbox.binarysearch;

import java.util.*;

public class BinarySearcher {

	private BinarySearcher() {
	}
	
	public static Person searchById(List<Person> c, Integer searchId) {

		Collections.sort(c, Comparator.comparingInt(p -> p.id));

		while(true) {
			int midle = c.size() / 2;
			final Person cp = c.get(midle);
			
			if (cp.id == searchId) {
				return cp;
			} else if ( midle == 0) {
				return null;
			}
			c = searchId < cp.id  ? c.subList(0, midle) : c.subList(midle, c.size());
		}
	}
	

}
