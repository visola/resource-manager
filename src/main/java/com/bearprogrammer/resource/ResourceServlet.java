package com.bearprogrammer.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(ResourceServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] files = request.getParameterValues("file");
		
		if (files == null || files.length == 0) {
			response.sendError(500, "No resources specified, 'file' parameter is empty.");
			return;
		}
		
		Map<String, String> contents = new HashMap<String, String>();

		// Set content type for the type of the first file requested
		response.setContentType(TypeFactory.getType(FilenameUtils.getExtension(files[0])).getContentType());
		
		for (String file : files) {
			try {
				String content = ResourceContext.getInstance().getResource(file);
				if (content == null) {
					response.sendError(404, "Resource not found: " + file);
					return;
				}
				contents.put(file, content);
			} catch (ResourceNotFoundException ex) {
				response.sendError(404, "Resource not found: " + file);
				logger.error("Resource not found: " + file, ex);
				return;
			} catch (Exception ex) {
				response.sendError(500, "Error while loading resource: " + file);
				logger.error("Error while loading resoruce: " + file, ex);
				return;
			}
		}
		
		
		String path = request.getRequestURI();
		String requestedExtension = FilenameUtils.getExtension(path);
		
		StringBuilder answer = new StringBuilder();
		if ("json".equals(requestedExtension)) {
			response.setContentType("application/json; charset=\"utf-8\"");
			ObjectMapper mapper = new ObjectMapper();
			Object [] result = new Object[files.length];
			for (int i = 0; i < files.length; i++) {
				HashMap<String, String> content = new HashMap<String, String>();
				content.put("path", files[i]);
				content.put("content", contents.get(files[i]));
				result[i] = content;
			}
			answer.append(mapper.writeValueAsString(result));
		} else {
			for (String file : files) {
				answer.append(contents.get(file));
				answer.append("\n");
			}
		}
		
		response.getWriter().write(answer.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}