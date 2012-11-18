package com.bearprogrammer.resource;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps a resource.
 * 
 * @author Vincius Isola
 */
public class ResourceWrapper {

	private Logger logger = LoggerFactory.getLogger(ResourceWrapper.class);
	
	protected String content;
	protected long lastModified = -1;
	protected final Type type;
	protected final Loader loader;
	protected final String identifier;

	/**
	 * Create a wrapper instance.
	 * 
	 * @param identifier
	 *            Identifier to access the resource.
	 * @param type
	 *            {@link Type} for this resource.
	 * @param loader
	 *            {@link Loader} to load the content from.
	 */
	public ResourceWrapper(String identifier, Type type, Loader loader) {
		super();
		this.type = type;
		this.loader = loader;
		this.identifier = identifier;
	}

	/**
	 * Return the content loaded and processed. If the resource was
	 * {@link Loader#lastUpdated(String) updated} it will be reloaded and
	 * reprocessed.
	 * 
	 * @return The content loaded and processed from the file.
	 * @throws IOException
	 * @throws ProcessingException
	 *             If an exception happens while processing the resource.
	 * @throws IOException
	 *             If any error occur while loading the resource.
	 */
	public synchronized String getContent() throws IOException, ProcessingException {
		if (lastModified < loader.lastUpdated(identifier)) {
			loadContent();
			content = type.processContent(content);
		}
		return content;
	}

	public String getIdentifier() {
		return identifier;
	}
	
	public Loader getLoader() {
		return loader;
	}

	public Type getType() {
		return type;
	}

	/**
	 * Load the content from the {@link Loader} associated with. The
	 * content will be stored in the {@link #content} variable.
	 * 
	 * @throws IOException
	 *             If any problem occurs while reading the content.
	 */
	protected void loadContent() throws IOException {
		logger.debug("Loading content '{}' using loader: {}", identifier, loader.getClass().getName());
		content = loader.loadResource(identifier);
		lastModified = loader.lastUpdated(identifier);
	}
	
}