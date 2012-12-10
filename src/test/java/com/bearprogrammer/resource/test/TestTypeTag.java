package com.bearprogrammer.resource.test;

import java.util.Map;

import com.bearprogrammer.resource.TypeTag;

public class TestTypeTag implements TypeTag {

	public static final String ATTRIBUTE_NAME = "test-attribute";
	public static final String TAG_NAME = "test-tag";

	@Override
	public String getAttributeName() {
		return ATTRIBUTE_NAME;
	}

	@Override
	public Map<String, String> getOtherAttributes() {
		return null;
	}

	@Override
	public String getTagName() {
		return TAG_NAME;
	}
	
}
