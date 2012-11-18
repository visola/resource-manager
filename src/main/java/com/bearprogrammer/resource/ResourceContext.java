package com.bearprogrammer.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralizes resource loading and caching.
 * 
 * @author Vinicius Isola
 */
public class ResourceContext {

	/**
	 * Private logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(ResourceContext.class);
	
	/**
	 * Singleton instance.
	 */
	private static ResourceContext instance = null;
	
	/**
	 * Return the <code>ResourceContext</code> instance.
	 * 
	 * @return The singleton instance.
	 */
	public static ResourceContext getInstance() {
		if (instance == null) {
			instance = new ResourceContext();
		}
		return instance;
	}

	/**
	 * Resource cache.
	 */
	private Map<String, Wrapper> resources = new HashMap<String, Wrapper>();
	
	private ResourceContext() {
		logger.debug("Resource context instantiated.");
	}
	
	/**
	 * Get the content of a resource using its extension as the type identifier.
	 * 
	 * @param identifier
	 *            The resource to get the content.
	 * @return The resource content already processed.
	 * @throws IOException
	 *             If any problem happens while loading the resource.
	 * @throws ProcessingException
	 *             If an exception happens while processing the resource.
	 * @throws ResourceNotFoundException
	 *             If no loader available was able to find the resource.
	 * @throws UnknownTypeException
	 *             If the type is unknown.
	 */
	public synchronized String getResource(String identifier) throws IOException, ProcessingException, ResourceNotFoundException,  UnknownTypeException {
		String extension = FilenameUtils.getExtension(identifier);
		logger.debug("Getting resource: {} ({})", identifier, extension);
		Wrapper result = resources.get(identifier);
		
		if (result == null) {
			logger.debug("Resource not in cache, loading...");
			
			Type type = TypeFactory.getType(extension);
			if (type == null) {
				throw new UnknownTypeException("No type available for the specified resource: " + extension);
			}
			
			Loader loader = LoaderFactory.getLoader(identifier);
			if (loader == null) {
				throw new ResourceNotFoundException("No loader available for the specified resource: " + identifier);
			}
			
			result = new Wrapper(identifier, type, loader);
			resources.put(identifier, result);
		}
		
		return result.getContent();
	}

}