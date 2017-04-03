package lv.nixx.poc.sandbox.repeatableelems;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

public class RepeatableElements {
	
	@Test
	public void test1() {
		final List<List<Integer>> seqs = Arrays.asList(
				Arrays.asList(1,2,3), 
				Arrays.asList(2,3,9), 
				Arrays.asList(7,7,4), 
				Arrays.asList(1,5,4),
				Arrays.asList(10,10),
				Arrays.asList(10)
			);
		
		getUniqueElemems(seqs).forEach(System.out::println);
	}
	
	public Collection<Integer> getUniqueElemems(List<List<Integer>> seq) {
		
		Set<Integer> notUnique = new HashSet<>();
		seq.stream()
		.flatMap(lst -> lst.stream().map(t->new Pair(lst.hashCode(), t)))
		.collect(Collectors.toMap( t->t.value, Function.identity(), (e, n) -> {
			if ( e.seqNum != n.seqNum ) {
				notUnique.add(n.value);
			}
			return n;
		}));
		
		return notUnique;
	}
	
	class Pair {
		int seqNum;
		int value;

		Pair(int seqNum, int value) {
			this.seqNum = seqNum;
			this.value = value;
		}
	}

}
