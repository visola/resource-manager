package com.bearprogrammer.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * Resource loader that can search for resources from the classpath or from
 * paths inside a web application.
 * 
 * @author Vinicius Isola
 */
public class Loader {

	/**
	 * Default encoding to be used by the loader.
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * Attribute name prefix for all configurations.
	 */
	public static final String ATTRIBUTE_NAME_PREFIX = Loader.class.getName();
	
	/**
	 * Attribute name to configure the encoding to be used by the loader.
	 */
	public static final String ATTRIBUTE_ENCODING = ATTRIBUTE_NAME_PREFIX + ".encoding";
	
	/**
	 * Encoding that will be used to load resources.
	 */
	String encoding = DEFAULT_ENCODING;
	
	/**
	 * Servlet context that will be used to configure this loader and load
	 * resources from web application structure.
	 */
	final ServletContext context;
	
	/**
	 * Create a new instance of <code>Loader</code> that will load any
	 * configuration from the map that was passed in. This will not look for
	 * resources inside a web application context.
	 * 
	 * @param configuration
	 *            The configuration that will be used to setup the instance.
	 */
	public Loader(Map<String, String> configuration) {
		context = null;
		
		String encoding = configuration.get(ATTRIBUTE_ENCODING);
		if (encoding != null) {
			this.encoding = encoding;
		}
	}
	
	/**
	 * Create a new instance of <code>Loader</code> that will search for
	 * resources in the web application and load configuration from the context.
	 * 
	 * @param context
	 *            Servlet context that will be used to search for real paths and
	 *            configuration.
	 */
	public Loader(ServletContext context) {
		this.context = context;
		
		String encoding = (String) context.getAttribute(ATTRIBUTE_ENCODING);
		if (encoding != null) {
			this.encoding = encoding;
		}
	}
	
	/**
	 * Get the last modified date for an identifier.
	 * 
	 * @param identifier
	 *            The identifier to get the last modified for.
	 * @return The last modified date for the resource or <code>null</code> if
	 *         not possible to identify it.
	 */
	public Long lastModified(String identifier) {
		URL url = locateResource(identifier);
		if (url != null && url.getProtocol().equals("file")) {
			File f = new File(url.getPath());
			return f.lastModified();
		}
		
		return null;
	}
	
	/**
	 * Load a resource content from some identifier. First it will search the
	 * classpath and then it will look into the web application to search for
	 * the resource in it.
	 * 
	 * @param identifier
	 *            The resource to look for.
	 * @return The content found.
	 * @throws IOException
	 *             If a problem happens while accessing or reading the resource.
	 * @throws ResourceNotFoundException
	 *             If the resource wasn't found.
	 */
	public String loadResource(String identifier) throws IOException, ResourceNotFoundException {
		URL url = locateResource(identifier);
		if (url != null) {
			InputStream inStream = url.openStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inStream, encoding));
			
			StringBuilder content = new StringBuilder();
			char [] buffer = new char[2048];
			int charsRead = -1;
			while ( (charsRead = in.read(buffer)) != -1) {
				content.append(buffer, 0, charsRead);
			}
			in.close();
			
			return content.toString();
		} else {
			throw new ResourceNotFoundException("Resource not found: " + identifier);
		}
	}
	
	/**
	 * Locate a resource using it's identifier. It will first search in the
	 * classpath and then in the web application if a servlet context is
	 * available.
	 * 
	 * @param identifier
	 *            Identifier to look for.
	 * @return A <code>URL</code> that points to the resource or null if it
	 *         wasn't found.
	 */
	URL locateResource(String identifier) {
		URL url = getClass().getClassLoader().getResource(identifier);
		if (url == null) {
			url = locateWebResource(identifier);
		}
		
		return url;
	}
	
	/**
	 * Locate a resource inside a web application if the
	 * <code>ServletContext</code> is not null.
	 * 
	 * @param identifier
	 *            The identifier to look for.
	 * @return A <code>URL</code> pointing to the file or <code>null</code> if
	 *         not found.
	 */
	URL locateWebResource(String identifier) {
		URL url = null;
		if (context != null) {
			String realPath = context.getRealPath(identifier);
			File f = new File(realPath);
			if (f.exists()) {
				url = urlFromFile(f);
			} else if (!identifier.startsWith("/WEB-INF/")){
				realPath = context.getRealPath("/WEB-INF/" + identifier);
				f = new File(realPath);
				if (f.exists()) {
					url = urlFromFile(f);
				}
			}
		}
		return url;
	}
	
	/**
	 * Convert a file to a URL.
	 * 
	 * @param f
	 *            The file to convert.
	 * @return A URL pointing to the file.
	 */
	URL urlFromFile(File f) {
		try {
			return f.toURI().toURL();
		} catch (MalformedURLException ex) {
			// Never happens
			return null;
		}
	}

}