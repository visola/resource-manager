package com.bearprogrammer.resource;

/**
 * Thrown every time a reference to an resource that doesn't exist happens.
 * 
 * @author Vincius Isola
 */
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

}