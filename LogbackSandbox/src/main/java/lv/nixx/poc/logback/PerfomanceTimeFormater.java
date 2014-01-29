package lv.nixx.poc.logback;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PerfomanceTimeFormater {
	
	static NumberFormat nf = new DecimalFormat("0,000000");
	
	public static String format(long time){
		return String.valueOf(nf.format(time));
	}

}
