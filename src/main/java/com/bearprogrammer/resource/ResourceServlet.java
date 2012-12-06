package com.bearprogrammer.resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceServlet extends HttpServlet {
	
	private static final String CONTENT_TYPE_JSON = "application/json; charset=\"utf-8\"";

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(ResourceServlet.class);
	
	/**
	 * Loader that will be used to load the resources.
	 */
	Loader loader;
	
	/**
	 * Resource cache.
	 */
	Map<String, ResourceWrapper> resources = new HashMap<String, ResourceWrapper>();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] files = request.getParameterValues("file");
		if (logger.isDebugEnabled()) {
			logger.debug("Resource servlet received request to load files: {}", Arrays.toString(files));
		}
		
		if (files == null || files.length == 0) {
			logger.warn("No resource specified, 'file' parameter is empty.");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No resource specified, 'file' parameter is empty.");
			return;
		}

		try {
			String requestedExtension = FilenameUtils.getExtension(request.getRequestURI());
			logger.trace("Request done with extension {}", requestedExtension);
			
			String answer = null;
			if ("json".equals(requestedExtension)) {
				/* If extension was .json, then it will return all resources as
				 * a JSON object. 
				 */
				response.setContentType(CONTENT_TYPE_JSON);
				answer = renderContentAsJson(files);
			} else {
				// Any other extension will set content type for the type of the first file requested
				response.setContentType(TypeFactory.getType(FilenameUtils.getExtension(files[0])).getContentType());
				answer = renderContentAsText(files);
			}
			
			logger.trace("--- Sending response to client ---\n{}", answer);
			response.getWriter().write(answer);
		} catch (ResourceNotFoundException ex) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found: " + ex.getMessage());
			logger.warn("Resource not found.", ex);
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading resource: " + ex.getMessage());
			logger.error("Error while loading resoruce.", ex);
		}
	}
	
	/**
	 * Not implemented. Will throw an <code>UnsupportedOperationException</code>
	 * if a post request comes in.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new UnsupportedOperationException("Should not use POST, should use GET to enable caching.");
	}
	
	String getContent(String identifier) throws Exception {
		logger.trace("Searching for '{}' in cache.", identifier);
		ResourceWrapper wrapper = resources.get(identifier);
		
		// If not in cache yet, load it
		if (wrapper == null) {
			long start = System.currentTimeMillis();
			logger.debug("'{}' not in cache. Loading and processing...", identifier);
			String extension = FilenameUtils.getExtension(identifier);
			
			Type type = TypeFactory.getType(extension);
			if (type == null) {
				logger.warn("No type available for the specified resource: {}", extension);
				throw new UnknownTypeException("No type available for the specified resource: " + extension);
			}
			
			String content = processContent(loader.loadResource(identifier), type);
			wrapper = new ResourceWrapper(identifier, type, content);
			resources.put(identifier, wrapper);
			logger.debug("Resource loaded and processed in {} ms.", System.currentTimeMillis() - start);
		} else {
			// If in cache, check last modified
			if (wrapper.getLastLoaded() < loader.lastModified(identifier)) {
				// If last loaded was before last modified, load it again
				wrapper.setContent(loader.loadResource(identifier));
			}
		}
		
		return wrapper.getContent();
	}
	
	String processContent(String content, Type type) throws ProcessingException {
		if (content != null && !content.isEmpty()) {
			for (Processor processor : type.getProcessors()) {
				content = processor.process(content);
			}
		}
		
		return content;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		loader = new Loader(config.getServletContext());
	}

	String renderContentAsJson(String[] files) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Object [] result = new Object[files.length];
		
		for (int i = 0; i < files.length; i++) {
			HashMap<String, String> content = new HashMap<String, String>();
			content.put("path", files[i]);
			content.put("content", getContent(files[i])); 
			result[i] = content;
		}
		
		return mapper.writeValueAsString(result);
	}

	String renderContentAsText(String[] files) throws Exception {
		StringBuilder buffer = new StringBuilder();
		for (String file : files) {
			buffer.append(getContent(file));
		}
		return buffer.toString();
	}

}