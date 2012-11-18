package com.bearprogrammer.resource.type;

import java.util.Map;

import com.bearprogrammer.resource.Type;


public class JavascriptType extends Type {
	
	private String [] supportedTypes = new String [] {"javascript", "script", "js"}; 

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
		return supportedTypes;
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