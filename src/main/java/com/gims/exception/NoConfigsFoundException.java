package com.gims.exception;

public class NoConfigsFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public NoConfigsFoundException(String message) {
		this.message = message;
	}

	public NoConfigsFoundException(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}