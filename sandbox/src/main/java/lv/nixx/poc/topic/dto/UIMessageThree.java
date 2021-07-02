package lv.nixx.poc.topic.dto;

public class UIMessageThree implements UIMessage {
	
	private final String message;

	public UIMessageThree(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "DtoThree [message=" + message + "]";
	}
	
}
