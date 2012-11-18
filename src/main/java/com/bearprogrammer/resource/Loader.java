package com.bearprogrammer.resource;

import java.io.IOException;

/**
 * A resource loader is responsible to load content from a path. Content
 * can be loaded from many different places like file system, classpath, etc.
 * 
 * @author Vinicius Isola
 */
public interface Loader {
	
	/**
	 * Check if this loader has the resource specified by the identifier.
	 * 
	 * @param identifier
	 *            The identifier to check for.
	 * @return True if this loader has access to the resource specified.
	 * @throws IOException
	 *             If any exception occurs while accessing the resource.
	 */
	public boolean hasResource(String identifier) throws IOException;
	
	/**
	 * Load a resource content using an identifier.
	 * 
	 * @param identifier
	 *            The resource identifier.
	 * @return Content for the resource or null if not found.
	 */
	public String loadResource(String identifier) throws IOException;
	
	/**
	 * Return the last time the resource was updated.
	 * 
	 * @param identifier
	 *            The resource identifier.
	 * @return A timestamp that tells the last time the resource was updated or
	 *         -1 if not possible to determine.
	 */
	public long lastUpdated(String identifier);

}