package com.bearprogrammer.resource.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.bearprogrammer.resource.Loader;


public class ClasspathLoader implements Loader {
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	private String encoding = DEFAULT_ENCODING;
	
	public ClasspathLoader() {
		super();
	}

	public String getEncoding() {
		return encoding;
	}

	@Override
	public boolean hasResource(String identifier) throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream(identifier);
		return in != null;
	}

	@Override
	public Long lastUpdated(String identifier) {
		URL url = getClass().getClassLoader().getResource(identifier);
		if (url.getProtocol().equals("file")) {
			return new File(url.getPath()).lastModified();
		}
		return null;
	}

	@Override
	public String loadResource(String identifier) throws IOException {
		InputStream inStream = getClass().getClassLoader().getResourceAsStream(identifier);
		
		StringBuilder content = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream, encoding));
		
		char [] buffer = new char[2048];
		int charsRead = -1;
		while ( (charsRead = in.read(buffer)) != -1) {
			content.append(buffer, 0, charsRead);
		}
		in.close();
		
		return content.toString();
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}