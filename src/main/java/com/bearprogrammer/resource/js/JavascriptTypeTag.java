package com.bearprogrammer.resource.js;

import java.util.Map;

import com.bearprogrammer.resource.TypeTag;

public class JavascriptTypeTag implements TypeTag {

	@Override
	public String getTagName() {
		return "script";
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
