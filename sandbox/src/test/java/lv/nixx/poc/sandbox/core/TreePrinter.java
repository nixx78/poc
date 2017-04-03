package lv.nixx.poc.sandbox.core;

import org.junit.Test;

public class TreePrinter {
	
	@Test
	public void printTree() {
		
		int size = 10;
		int d = 0;
		
		for (int i = 1; i < size; i++) {
			
			if (i % 2 != 0 ) { 
				for (int j = 0; j < size; j++) {
					d = ( size - i ) / 2;
					System.out.print(j < d || j > d + i  ? '-' : '*');
				}
				System.out.println();
			}	
		}
		
		
	}
	
	@Test
	public void printTree1() {
		int size = 20;
		for (int i = 1; i < size; i = i + 2) {
			String t = new String(new char[( size - i ) / 2]).replace("\0", "-");
			System.out.println(t +  new String(new char[i]).replace("\0", "*") + t);
		}
	}
	

}
