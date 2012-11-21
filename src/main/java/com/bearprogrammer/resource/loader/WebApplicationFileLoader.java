package com.bearprogrammer.resource.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import com.bearprogrammer.resource.Loader;

public class WebApplicationFileLoader implements Loader {
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	ServletContext context;
	String encoding = DEFAULT_ENCODING;
	
	public WebApplicationFileLoader() {
		
	}
	
	public ServletContext getContext() {
		return context;
	}

	public String getEncoding() {
		return encoding;
	}

	File getFile(String identifier) {
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
	public Long lastUpdated(String identifier) {
		File f = getFile(identifier);
		if (f == null) return 0L;
		return f.lastModified();
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

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}