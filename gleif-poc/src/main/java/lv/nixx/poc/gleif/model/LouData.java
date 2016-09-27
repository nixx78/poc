package lv.nixx.poc.gleif.model;

import java.util.Collection;
import java.util.HashSet;

public class LouData {

	private int valuesInHeader;
	
	private String name;
	private String louLei;

	private Collection<LeiData> leiData = new HashSet<>();
	
}
