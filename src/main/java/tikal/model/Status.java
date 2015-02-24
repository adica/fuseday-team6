package tikal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Status {
	
	private enum STATUS {
		success, fail
	}
	
	private STATUS status = STATUS.success;
	
	@JsonInclude(Include.NON_NULL)
	private String error;

	public STATUS getStatus() {
		return status;
	}
	
	public void setStatus(STATUS status) {
		this.status = status;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
}
