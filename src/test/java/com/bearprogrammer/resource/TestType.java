package com.bearprogrammer.resource;

import java.util.Map;

public class TestType extends Type {

	public static final String ATTRIBUTE_NAME = "test-attribute";
	public static final String CONTENT_TYPE = "test/text";
	public static final String[] SUPPORTED_TYPES = new String[] { "test" };
	public static final String TAG_NAME = "test-tag";

	@Override
	public String getAttributeName() {
		return ATTRIBUTE_NAME;
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public Map<String, String> getOtherAttributes() {
		return null;
	}

	@Override
	public String[] getSupportedTypes() {
		return SUPPORTED_TYPES;
	}

	@Override
	public String getTagName() {
		return TAG_NAME;
	}

}
