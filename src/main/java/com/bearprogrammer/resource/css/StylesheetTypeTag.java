package com.bearprogrammer.resource.css;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bearprogrammer.resource.TypeTag;

public class StylesheetTypeTag implements TypeTag {
	
	static Map<String, String> attributes = null;
	
	static {
		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("type", "text/css");
		temp.put("rel", "stylesheet");
		attributes = Collections.unmodifiableMap(temp);
	}
	
	@Override
	public String getAttributeName() {
		return "href";
	}

	@Override
	public Map<String, String> getOtherAttributes() {
		return attributes;
	}

	@Override
	public String getTagName() {
		return "link";
	}

}
