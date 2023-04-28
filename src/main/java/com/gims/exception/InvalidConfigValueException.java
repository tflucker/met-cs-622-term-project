package com.gims.exception;

public class InvalidConfigValueException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidConfigValueException(String message) {
		this.message = message;
	}

	public InvalidConfigValueException(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
