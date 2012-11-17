package com.bearprogrammer.web.resource.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import com.bearprogrammer.web.resource.Loader;
import com.bearprogrammer.web.resource.ResourceContextListener;


public class ClasspathLoader implements Loader {
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	private String encoding = DEFAULT_ENCODING;
	
	public ClasspathLoader() {
		ServletContext context = ResourceContextListener.getInstance().getServletContext();
		String encoding = context.getInitParameter(this.getClass().getName().concat(".encoding"));
		if (encoding != null) this.encoding = encoding;
	}

	@Override
	public boolean hasResource(String identifier) throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream(identifier);
		return in != null;
	}

	@Override
	public String loadResource(String identifier) throws IOException {
		InputStream inStream = getClass().getClassLoader().getResourceAsStream(identifier);
		
		StringBuilder content = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream, encoding));
		
		String line = null;
		while ( (line = in.readLine()) != null) {
			content.append(line);
			content.append("\n");
		}
		
		in.close();
		
		return content.toString();
	}

	@Override
	public long lastUpdated(String identifier) {
		return 0;
	}

}