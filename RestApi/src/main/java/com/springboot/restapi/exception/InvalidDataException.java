package com.springboot.restapi.exception;

import java.util.Map;

public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	private Map<String, String> errorData;

	public InvalidDataException() {
	}

	public InvalidDataException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public InvalidDataException(String errorCode, String errorMessage, Map<String, String> errorData) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorData = errorData;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<String, String> getErrorData() {
		return errorData;
	}

	public void setErrorData(Map<String, String> errorData) {
		this.errorData = errorData;
	}

}

