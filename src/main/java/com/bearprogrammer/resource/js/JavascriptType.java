package com.bearprogrammer.resource.js;

import java.util.Map;

import com.bearprogrammer.resource.Type;


public class JavascriptType extends Type {
	
	public static final String [] SUPPORTED_TYPES = new String [] {"javascript", "script", "js"}; 

	@Override
	public String getTagName() {
		return "script";
	}

	@Override
	public String getContentType() {
		return "text/javascript";
	}

	@Override
	public String[] getSupportedTypes() {
		return SUPPORTED_TYPES;
	}

	@Override
	public String getAttributeName() {
		return "src";
	}

	@Override
	public Map<String, String> getOtherAttributes() {
		return null;
	}

}