package lv.nixx.poc.sandbox.mediana;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

//http://statanaliz.info/metody/opisanie-dannyx/58-mediana-v-statistike
public class MedianaCalculation {

	@Test
	public void medianaCalculation() {

		List<Entry> lst = new ArrayList<>();
		lst.add(new Entry(100, 200, 20));
		lst.add(new Entry(201, 300, 50));
		lst.add(new Entry(301, 400, 60));
		lst.add(new Entry(401, 500, 40));
		lst.add(new Entry(501, 600, 30));

		addCalculateAccumulatedValues(lst);
		medianaCalculate(lst);
	}

	private void addCalculateAccumulatedValues(List<Entry> lst) {
		final Integer sum = lst.stream().map(e -> e.n).reduce(0, (a, b) -> a + b);

		for (int i = 0; i < lst.size(); i++) {
			Entry e = lst.get(i);
			int ePrevN = 0;
			if (i > 0) {
				Entry prevE = lst.get(i - 1);
				ePrevN = prevE.nAccumulated;
			}
			final int na = e.n + ePrevN;
			e.nAccumulated = na;
			e.nAccumulatedPerc = 100 * ((double) na / (double) sum);
		}

		lst.forEach(System.out::println);
	}

	private void medianaCalculate(List<Entry> lst) {

		final double sum = lst.stream().map(e -> e.n).reduce(0, (a, b) -> a + b).doubleValue();

		int nPrevAccum = 0;
		boolean notFound = true;

		Entry e = null;

		int i = 0;
		while (notFound && i < lst.size()) {
			e = lst.get(i);
			if (e.nAccumulatedPerc >= 50) {
				if (i > 0) {
					Entry prev = lst.get(i - 1);
					nPrevAccum = prev.nAccumulated;
				}
				notFound = false;
			}
			i++;
		}

		double mediana = e.from + (100 * ((sum / 2) - nPrevAccum) / e.n);
		System.out.println("Mediana:" + mediana);
	}

	class Entry {
		int from;
		int to;
		int n;
		int nAccumulated;
		double nAccumulatedPerc;

		Entry(int from, int to, int n) {
			this.from = from;
			this.to = to;
			this.n = n;
		}

		@Override
		public String toString() {
			return "Entry [from=" + from + ", to=" + to + ", n=" + n + ", nAccumulated=" + nAccumulated
					+ ", nAccumulatedPerc=" + nAccumulatedPerc + "]";
		}

	}

}
