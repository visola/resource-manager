package com.bearprogrammer.resource.test;

import java.io.IOException;

import com.bearprogrammer.resource.Loader;

public class TestLoader implements Loader {
	
	public static final String IDENTIFIER = "this_is_weird.test";
	
	public static long lastModified = System.currentTimeMillis();
	public static String content = Long.toString(lastModified);

	@Override
	public boolean hasResource(String identifier) throws IOException {
		return IDENTIFIER.equals(identifier);
	}

	@Override
	public long lastUpdated(String identifier) {
		return lastModified;
	}

	@Override
	public String loadResource(String identifier) throws IOException {
		if (IDENTIFIER.equals(identifier)) {
			return content;
		}
		return null;
	}

	public static void generateNewContent() {
		lastModified = System.currentTimeMillis();
		content = Long.toString(lastModified);
	}
	
}
