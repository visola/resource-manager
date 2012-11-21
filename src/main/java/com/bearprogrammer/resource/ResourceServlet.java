package com.bearprogrammer.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(ResourceServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] files = request.getParameterValues("file");
		
		if (files == null || files.length == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No resources specified, 'file' parameter is empty.");
			return;
		}

		try {
			Map<String, String> contents = loadFiles(files);
			
			String requestedExtension = FilenameUtils.getExtension(request.getRequestURI());
			
			String answer = null;
			if ("json".equals(requestedExtension)) {
				answer = renderContentAsJson(response, files, contents);
			} else {
				// Set content type for the type of the first file requested
				response.setContentType(TypeFactory.getType(FilenameUtils.getExtension(files[0])).getContentType());
				answer = renderContentAsText(files, contents);
			}
			
			response.getWriter().write(answer);
		} catch (ResourceNotFoundException ex) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found: " + ex.getMessage());
			logger.error("Resource not found.", ex);
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading resource: " + ex.getMessage());
			logger.error("Error while loading resoruce.", ex);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new UnsupportedOperationException("Should not use POST, should use GET to enable caching.");
	}

	Map<String, String> loadFiles(String[] files) throws IOException, ProcessingException, ResourceNotFoundException, UnknownTypeException {
		Map<String, String> contents = new HashMap<String, String>();
		
		for (String file : files) {
			String content = ResourceContext.getInstance().getResource(file);
			contents.put(file, content);
		}
		
		return contents;
	}

	String renderContentAsJson(HttpServletResponse response, String[] files, Map<String, String> contents) throws IOException, JsonGenerationException, JsonMappingException {
		response.setContentType("application/json; charset=\"utf-8\"");
		
		ObjectMapper mapper = new ObjectMapper();
		Object [] result = new Object[files.length];
		
		for (int i = 0; i < files.length; i++) {
			HashMap<String, String> content = new HashMap<String, String>();
			content.put("path", files[i]);
			content.put("content", contents.get(files[i]));
			result[i] = content;
		}
		
		return mapper.writeValueAsString(result);
	}

	String renderContentAsText(String[] files, Map<String, String> contents) {
		StringBuilder buffer = new StringBuilder();
		for (String file : files) {
			buffer.append(contents.get(file));
			buffer.append("\n");
		}
		return buffer.toString();
	}

}