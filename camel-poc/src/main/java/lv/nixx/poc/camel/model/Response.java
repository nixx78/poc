package lv.nixx.poc.camel.model;

public class Response {
	
	private int id;
	private boolean isSuccess;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public String toString() {
		return "Response [id=" + id + ", isSuccess=" + isSuccess + "]";
	}

}
