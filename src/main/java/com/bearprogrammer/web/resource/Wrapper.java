package com.bearprogrammer.web.resource;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps a resource.
 * 
 * @author Vincius Isola (vinicusisola@gmail.com)
 */
public class Wrapper {

	private Logger logger = LoggerFactory.getLogger(Wrapper.class);
	
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
	public Wrapper(String identifier, Type type, Loader loader) {
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
	 * @throws Exception
	 *             If any error occur while loading or processing the resource.
	 */
	public synchronized String getContent() throws Exception {
		if (lastModified < loader.lastUpdated(identifier)) {
			loadContent();
			processContent();
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
	 * Check if the file changed since it was last loaded.
	 * 
	 * @return True if the file changed.
	 */
	public boolean isChanged () {
		if (loader.lastUpdated(identifier) > lastModified) return true;
		return false;
	}

	/**
	 * Check if the content was loaded at least once.
	 * 
	 * @return True if the content was loaded at least once.
	 */
	public boolean isLoaded() {
		return lastModified == -1;
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
	
	/**
	 * Uses all the {@link Processor processors} available to process the content and
	 * store the result in the {@link #content} variable.
	 */
	protected void processContent() throws Exception {
		List<Processor> processors = type.getProcessors();
		if (processors != null) {
			for (Processor processor : processors) {
				logger.debug("Processing content '{}' using processor: {}", identifier, processor.getClass().getName());
				content = processor.process(content);
			}
		}
	}
	
}