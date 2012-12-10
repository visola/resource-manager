package com.bearprogrammer.resource.js;

import com.bearprogrammer.resource.Type;
import com.bearprogrammer.resource.TypeTag;

public class JavascriptType extends Type {
	
	static final TypeTag typeTag = new JavascriptTypeTag();
	
	public static final String [] SUPPORTED_TYPES = new String [] {"javascript", "script", "js"}; 

	@Override
	public String getContentType() {
		return "text/javascript";
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