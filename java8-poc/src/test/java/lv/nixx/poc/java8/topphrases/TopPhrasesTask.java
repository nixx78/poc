package lv.nixx.poc.java8.topphrases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class TopPhrasesTask {

	public static void main(String[] args) throws IOException {

		String words = 
				"Foobar Candy | Olympics 2012 | PGA | CNET | Microsoft Bing\n"+
				"White | White blue | PGA | PGA | RED\n";

		TopPhrases topOccurrence = new TopPhrases(3);

		try (BufferedReader br = new BufferedReader(new StringReader(words))) {
					br.lines()
					.flatMap(s -> Arrays.asList(s.split("\\|")).stream())
					.map( s -> s.trim())
					.collect(Collectors.toConcurrentMap(w -> w.toLowerCase(), w -> 1, Integer::sum))
					.forEach((s, integer) -> topOccurrence.add(new Tuple(s, integer)));

			System.out.println(topOccurrence);
		}

	}

	static class TopPhrases {
		private final PriorityQueue<Tuple> minHeap;
		private final int maxSize;

		public TopPhrases(int maxSize) {
			this.maxSize = maxSize;
			this.minHeap = new PriorityQueue<Tuple>(Comparator.comparingInt(wc -> wc.count));
		}

		public void add(Tuple data) {
			if (minHeap.size() < maxSize) { // Big O(1)
				minHeap.offer(data); // Big O(log(k)) where k is the number of
									 // top occurrences required
			} else if (minHeap.peek().count < data.count) { // Big O(1)
				minHeap.poll(); // Big O(log(k))
				minHeap.offer(data); // Big O(log(k))
			}
		}

		@Override
		public String toString() {
			return "TopPhrases {" + "minHeap=" + minHeap + ", maxSize=" + maxSize + '}';
		}
	}

	static class Tuple {
		final String word;
		final int count;

		Tuple(String word, int count) {
			this.word = word;
			this.count = count;
		}

		@Override
		public String toString() {
			return "{" + "word='" + word + '\'' + ", count=" + count + '}' + "\r\n";
		}
	}
}