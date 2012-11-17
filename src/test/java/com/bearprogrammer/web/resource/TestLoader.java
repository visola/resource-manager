package com.bearprogrammer.web.resource;

import java.io.IOException;

public class TestLoader implements Loader {
	
	public static final String IDENTIFIER = "This is a weird protocol";
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
