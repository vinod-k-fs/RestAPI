package com.springboot.restapi.utilities;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ResponseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer status;
	private Object data;
	private String message;
	private String statusCode;

	public ResponseVO() {

	}

	public ResponseVO(Integer status, String message, String statusCode) {
		super();
		this.status = status;
		this.message = message;
		this.statusCode = statusCode;
	}

	public ResponseVO(Integer status, String message, String statusCode, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
