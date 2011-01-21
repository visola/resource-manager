package br.com.depasser.web.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ResourceWrapper {

	private Logger logger = LoggerFactory.getLogger(ResourceWrapper.class);
	protected final File file;
	protected String content;
	private long lastModified = -1;
	protected final ResourceType type;

	public ResourceWrapper(ResourceType type, File file) {
		super();
		this.file = file;
		this.type = type;
	}

	/**
	 * Return the content already processed. 
	 * 
	 * @return The content loaded and processed from the file.
	 * @throws IOException
	 * @see {@link #prepareResource()}
	 */
	public String getContent() throws IOException {
		prepareResource();
		return content;
	}

	public File getFile() {
		return file;
	}

	public long getLastModified() {
		return lastModified;
	}

	public ResourceType getType() {
		return type;
	}
	
	/**
	 * Check if the file changed since it was last loaded.
	 * 
	 * @return True if the file changed.
	 */
	public boolean isChanged () {
		if (file.lastModified() > lastModified) return true;
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
	 * Load the content from the file and return it as a <code>String</code>.
	 * 
	 * @return The content loaded from the file.
	 * @throws IOException
	 *             If any problem occurs while reading the file.
	 */
	protected String loadFileContent() throws IOException {
		logger.debug("Loading resource: " + file.getName());
		StringBuilder c = new StringBuilder();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
			char [] buffer = new char[1024];
			int charsRead = -1;
			while ( (charsRead = reader.read(buffer)) != -1) {
				c.append(buffer, 0, charsRead);
			}
		} catch (IOException ioe) {
			logger.error("Error while loading resource: " + file.getName(), ioe);
			throw ioe;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) { 
					// Do nothing 
				}
			}
		}
		
		lastModified = file.lastModified();
		return c.toString();
	}

	/**
	 * If the content was not loaded before, it will be loaded and processed
	 * before returned. If the content was loaded before it will be returned
	 * immediately. If the file in the file system was changed, the content will
	 * be reloaded and reprocessed before returned.
	 * 
	 * @throws IOException
	 */
	public synchronized void prepareResource() throws IOException {
		if (isChanged()) {
			String path = file.getName();
			content = loadFileContent();
			logger.debug("Processing resource: " + path);
			processContent();
			logger.debug("Resource ready: " + path);
		}
	}
	
	/**
	 * This method will be called just after the content was loaded from the
	 * file. It is the place to add compressing or obfuscating algorithms.
	 */
	protected abstract void processContent();
	
}