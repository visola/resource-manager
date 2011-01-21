package br.com.depasser.web.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;


public class ResourceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected Map<String, ResourceWrapper> resources = new HashMap<String, ResourceWrapper>();
	private static ResourceServlet instance = null;
	protected ResourceType [] types;
	
    public ResourceServlet() {
        super();
        instance = this;
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] files = request.getParameterValues("file");
		
		if (files == null || files.length == 0) {
			response.sendError(404, "No resources specified, 'file' parameter is empty.");
			return;
		}
		
		List<String> wrapErrors = wrapResources(files);
		if (wrapErrors.size() > 0) {
			sendErrorResponse(404, response, wrapErrors);
			return;
		}
		
		List<String> loadingErrors = prepareResources(files);
		if (loadingErrors.size() > 0) {
			sendErrorResponse(500, response, loadingErrors);
			return;
		}
		
		String path = request.getRequestURI();
		String requestedExtension = getExtension(path);
		
		StringBuilder answer = new StringBuilder();
		if ("json".equals(requestedExtension)) {
			response.setContentType("application/json; charset=\"utf-8\"");
			ObjectMapper mapper = new ObjectMapper();
			Object [] result = new Object[files.length];
			for (int i = 0; i < files.length; i++) {
				ResourceWrapper wrapper = resources.get(files[i]);
				HashMap<String, String> content = new HashMap<String, String>();
				content.put("path", files[i]);
				content.put("content", wrapper.getContent());
				result[i] = content;
			}
			answer.append(mapper.writeValueAsString(result));
		} else {
			for (String file : files) {
				ResourceWrapper wrapper = resources.get(file);
				response.setContentType(wrapper.getType().getContentType() + "; charset=\"utf-8\"");
				answer.append(wrapper.getContent());
				answer.append("\n");
			}
		}
		
		response.getWriter().write(answer.toString());
	}

	private List<String> prepareResources(String [] files) {
		List<String> errors = new ArrayList<String>();
		
		for (String file : files) {	
			try {
				ResourceWrapper resource = resources.get(file);
				resource.prepareResource();
			} catch (Exception ioe) {
				errors.add("Error reading file: " + file + " -> " + ioe.getMessage());
			}
		}
		
		return errors;
	}

	private void sendErrorResponse(int errorStatus, HttpServletResponse response, List<String> errors) throws IOException {
		StringBuilder message = new StringBuilder();
		message.append("Errors while loading resources: ");
		
		for (int i = 0; i < errors.size(); i++) {
			message.append(errors.get(i));
			if (i != errors.size() - 1) message.append(",");
		}
		
		response.sendError(errorStatus, message.toString());
		return;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected String getExtension(String path) {
		int lastIndexOf = path.lastIndexOf(".");
		
		if (lastIndexOf == -1) {
			return null;
		}
		
		return path.substring(lastIndexOf + 1, path.length());
	}

	public static ResourceServlet getInstance() {
		return instance;
	}

	protected ResourceType getResourceType(String extension) {
		for (ResourceType type : types) {
			for (String accept : type.getSupportedTypes()) {
				if (accept.equals(extension)) {
					return type;
				}
			}
		}
		return null;
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		types = ResourceType.getTypes();
	}
	
	protected List<String> wrapResources(String[] files) {
		List<String> errors = new ArrayList<String>();
		
		for (String path : files) {
			// Resource may already have been loaded
			ResourceWrapper resource = resources.get(path);
			
			// If resource not loaded
			if (resource == null) {
				String extension = getExtension(path);
				
				ResourceType type = getResourceType(extension);
				if (type == null) {
					errors.add("Unknown type for: " + path);
					continue;
				}
				
				ResourceWrapper wrapper = type.wrapContent(path);
				
				// Check for file
				if (!wrapper.getFile().exists()) {
					errors.add("File not found: " + path);
					continue;
				}
				
				resources.put(path, wrapper);
			}
		}
		
		return errors;
	}
	
}