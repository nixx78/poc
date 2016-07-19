package lv.nixx.poc.camel.domain;

public class Statistic {

	private int id;
	private String fileName;
	private int count;
	
	public Statistic(String fileName, int count) {
		this.fileName = fileName;
		this.count = count;
	}

	public String getFileName() {
		return fileName;
	}

	public int getCount() {
		return count;
	}
	
	public int getId(){
		return id;
	}

	@Override
	public String toString() {
		return "Statistic [fileName=" + fileName + ", count=" + count + "]";
	}

}
