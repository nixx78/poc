package lv.nixx.poc.camel.integration;

public class BussinessError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BussinessError(String message) {
		super(message);
	}

}
