package com.bearprogrammer.resource;

/**
 * Wraps a resource.
 * 
 * @author Vincius Isola
 */
public class ResourceWrapper {

	protected String content;
	protected long lastLoaded = -1;
	protected final Type type;
	protected final String identifier;

	/**
	 * Create a wrapper instance.
	 * 
	 * @param identifier
	 *            Identifier to access the resource.
	 * @param type
	 *            {@link Type} for this resource.
	 * @param content
	 *            The resource content.
	 */
	public ResourceWrapper(String identifier, Type type, String content) {
		super();
		this.content = content;
		this.type = type;
		this.identifier = identifier;
		lastLoaded = System.currentTimeMillis();
	}

	public String getContent() {
		return content;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public long getLastLoaded() {
		return lastLoaded;
	}

	public Type getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
		this.lastLoaded = System.currentTimeMillis();
	}

}