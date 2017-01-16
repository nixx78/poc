package lv.nixx.poc.sorting;

import org.junit.Test;

public class QuickSort {

	@Test
	public void quickSortTest() {
		int[] a = new int[]{99,66,1,2,55,44,33,6,1};
		quickSort(a);
	}

	public void quickSort(int[] array) {
		doSort(array, 0, array.length - 1);
	}

	private void doSort(int[] array, int start, int end) {
		if (start >= end)
			return;
		
		int i = start, j = end;
		int cur = i - (i - j) / 2;
		while (i < j) {
			while (array[i] <= array[cur] && i < cur ) {
				i++;
			}
			while (array[cur] <= array[j] && j > cur) {
				j--;
			}
			if (i < j) {
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
				
				cur = i == cur ? j : j == cur ? i : cur;
			}
		}
		doSort(array, start, cur);
		doSort(array, cur + 1, end);
	}

}
