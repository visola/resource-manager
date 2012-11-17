package com.bearprogrammer.web.resource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceContextListener implements ServletContextListener {

	private static Logger logger = LoggerFactory.getLogger(ResourceContextListener.class);
	
	private static ResourceContextListener instance = null;
	
	public static ResourceContextListener getInstance() {
		return instance;
	}

	private Map<String, Wrapper> resources = new HashMap<String, Wrapper>();
	
	private ServletContext context = null;
	
	public ResourceContextListener() {
		if (instance == null) {
			instance = this;
		} else {
			logger.warn("!!!A resource context was already initialized. This can create strange bugs, please be careful!!!");
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Destroying resource management context...");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		logger.info("Initializing resource management context: {}", context.getContextPath());
	}
	
	public synchronized String getResource(String identifier, String typeId) throws Exception {
		Wrapper result = resources.get(identifier);
		
		if (result == null) {
			Loader loader = LoaderFactory.getLoader(identifier);
			if (loader == null) {
				throw new ResourceNotFoundException("No loader available for the specified resource: " + identifier);
			}
			
			Type type = TypeFactory.getType(typeId);
			if (type == null) {
				throw new IllegalArgumentException("No type available for the specified resource: " + typeId);
			}
			
			result = new Wrapper(identifier, type, loader);
			resources.put(identifier, result);
		}
		
		return result.getContent();
	}

	public ServletContext getServletContext() {
		return context;
	}
	
}