package com.bearprogrammer.resource.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import com.bearprogrammer.resource.Loader;
import com.bearprogrammer.resource.ResourceContext;


public class WebApplicationFileLoader implements Loader {
	// TODO - Need to re-write this class
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	private ServletContext context;
	
	private String encoding = DEFAULT_ENCODING;
	
	public WebApplicationFileLoader() {
//		ResourceContext instance = ResourceContext.getInstance();
//		if (instance != null) {
//			context = instance.getServletContext();
//			if (context != null) {
//				String encoding = context.getInitParameter(WebApplicationFileLoader.class.getName().concat(".encoding"));
//				if (encoding != null) this.encoding = encoding;
//			}
//		}
	}
	
	private File getFile(String identifier) {
		File f = null;
		if (context != null) {
			String realPath = context.getRealPath(identifier);
			f = new File(realPath);
		}
		return f;
	}

	@Override
	public boolean hasResource(String identifier) throws IOException {
		File f = getFile(identifier);
		return f != null && f.exists() && f.isFile();
	}

	@Override
	public String loadResource(String identifier) throws IOException {
		File f = getFile(identifier);
		
		StringBuilder content = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), encoding));
		
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
		File f = getFile(identifier);
		if (f == null) return 0;
		return f.lastModified();
	}

}