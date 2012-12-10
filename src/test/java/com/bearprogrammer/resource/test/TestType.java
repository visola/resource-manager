package com.bearprogrammer.resource.test;

import com.bearprogrammer.resource.Type;
import com.bearprogrammer.resource.TypeTag;

public class TestType extends Type {

	public static final TypeTag typeTag = new TestTypeTag();
	public static final String CONTENT_TYPE = "test/text";
	public static final String[] SUPPORTED_TYPES = new String[] { "test" };
	
	@Override
	public String getContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public String[] getSupportedTypes() {
		return SUPPORTED_TYPES;
	}

	@Override
	public TypeTag getTypeTag() {
		return typeTag;
	}

}
