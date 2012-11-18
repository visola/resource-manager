package com.bearprogrammer.resource;

/**
 * Thrown when a reference to an unknown type is passed.
 * 
 * @author Vinicius Isola
 */
public class UnknownTypeException extends Exception {

	/**
	 * Serialization version.
	 */
	private static final long serialVersionUID = 1L;

	public UnknownTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownTypeException(String message) {
		super(message);
	}

}
