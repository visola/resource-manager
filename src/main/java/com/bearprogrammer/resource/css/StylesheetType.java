package com.bearprogrammer.resource.css;

import com.bearprogrammer.resource.Type;
import com.bearprogrammer.resource.TypeTag;


public class StylesheetType extends Type {
	
	static final TypeTag typeTag = new StylesheetTypeTag();
	
	String [] supportedTypes = new String [] {"css", "stylesheet"};

	@Override
	public String getContentType() {
		return "text/css";
	}

	@Override
	public String[] getSupportedTypes() {
		return supportedTypes;
	}

	@Override
	public TypeTag getTypeTag() {
		return typeTag;
	}
	
}