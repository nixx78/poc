package lv.nixx.poc.sorting.range;

import static org.junit.Assert.*;
import static lv.nixx.poc.sorting.range.BelongsToRange.RangeLabel.*;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.Test;

public class BelongsToRange {
	
	@Test
	public void belongsToRangeWithoutGaps() {
		
		NavigableMap<Integer, RangeLabel> rangeMap = new TreeMap<>();
		rangeMap.put(1, Low);
		rangeMap.put(1000, Medium);
		rangeMap.put(2000, High);
		rangeMap.put(3000, ExtraHigh);
		
		assertEquals(Low, rangeMap.floorEntry(500).getValue());
		assertEquals(Low, rangeMap.floorEntry(999).getValue());
		assertEquals(Medium, rangeMap.floorEntry(1000).getValue());
		assertEquals(ExtraHigh, rangeMap.floorEntry(7777).getValue());
	}
	
	@Test
	public void belongsToRangeWithGaps() {
		
		NavigableMap<Integer, Range> rangeMap = new TreeMap<>();
		
		rangeMap.put(1, new Range(999, Low));
		rangeMap.put(1500, new Range(3000, Medium));
		rangeMap.put(4000, new Range(5000, High));
		
		assertEquals(OutOfRange, getLabel(rangeMap, 0) );
		assertEquals(Low, getLabel(rangeMap, 500));
		assertEquals(OutOfRange, getLabel(rangeMap, 1200));
		assertEquals(Medium, getLabel(rangeMap, 1500));
		assertEquals(High, getLabel(rangeMap, 4500));
		assertEquals(OutOfRange, getLabel(rangeMap, 7777));
	}
	
	private RangeLabel getLabel(NavigableMap<Integer, Range> rangeMap, int number) {
		final Entry<Integer, Range> floorEntry = rangeMap.floorEntry(number);
		
		return floorEntry == null || number > floorEntry.getValue().to ? OutOfRange : floorEntry.getValue().label;
	}
	
	
	static class Range {
		int to;
		RangeLabel label;
		
		Range(int to, RangeLabel label) {
			this.to = to;
			this.label = label;
		}
	}
	
	enum RangeLabel {
		Low, Medium, High, ExtraHigh, OutOfRange
	}

}
