package com.gims.exception;

/**
 * Custom exception thrown if there is an error while translating a record from
 * an imported data file.
 * 
 * @author Tim Flucker
 *
 */
public class InvalidImportException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidImportException(String message) {
		this.message = message;
	}

	public InvalidImportException(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
