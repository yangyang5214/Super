package com.springboot.common.dto;

import java.util.List;

public class ListResponseDto<T> {

	private Boolean success = true;

	private String message;

	private List<T> obj;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getObj() {
		return obj;
	}

	public void setObj(List<T> obj) {
		this.obj = obj;
	}
}
