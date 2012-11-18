package com.bearprogrammer.resource;

import java.io.IOException;

import com.bearprogrammer.resource.Loader;

public class TestLoader implements Loader {
	
	public static final String IDENTIFIER = "this_is_weird.test";
	public static final String CONTENT = "Hello World!";

	@Override
	public boolean hasResource(String identifier) throws IOException {
		return IDENTIFIER.equals(identifier);
	}

	@Override
	public String loadResource(String identifier) throws IOException {
		if (IDENTIFIER.equals(identifier)) {
			return CONTENT;
		}
		return null;
	}

	@Override
	public long lastUpdated(String identifier) {
		return 0;
	}

}
