package com.bearprogrammer.resource;

/**
 * Thrown when there is a {@link Processor#process(String) processing} error.
 * 
 * @author Vinicius Isola
 */
public class ProcessingException extends Exception {

	/**
	 * Serialization version.
	 */
	private static final long serialVersionUID = 1L;

	public ProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessingException(String message) {
		super(message);
	}

}
