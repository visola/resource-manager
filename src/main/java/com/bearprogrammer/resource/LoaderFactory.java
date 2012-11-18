package com.bearprogrammer.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load all available {@link Loader}s registered as service for the
 * <code>Loader</code> interface.
 * 
 * @author Vinicius Isola
 */
public class LoaderFactory {
	
	private static Logger logger = LoggerFactory.getLogger(LoaderFactory.class);
	private static Loader [] loaders = null;

	/**
	 * Get a specified loader that contains the specified resource.
	 * 
	 * @param identifier
	 *            Resource identifier.
	 * @return A loader that has the resource or null if none found.
	 * @throws IOException
	 *             If any exception happens while checking for a resource.
	 */
	public static Loader getLoader(String identifier) throws IOException {
		for (Loader loader : getLoaders()) {
			if (loader.hasResource(identifier)) {
				return loader;
			}
		}
		
		return null;
	}
	
	/**
	 * Load all loaders available from the classpath.
	 * 
	 * @return An array with all loaders available.
	 */
	public static Loader [] getLoaders() {
		if (loaders == null) {
			List<Loader> loaderList = new ArrayList<Loader>();
			
			ServiceLoader<Loader> serviceLoader = ServiceLoader.load(Loader.class);
			for (Loader loader : serviceLoader) {
				logger.debug("Resource loader found: " + loader.getClass().getName());
				loaderList.add(loader);
			}
			loaders = loaderList.toArray(new Loader[loaderList.size()]);
		}
		return loaders;
	}

}