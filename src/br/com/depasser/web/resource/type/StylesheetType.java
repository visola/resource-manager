package br.com.depasser.web.resource.type;

import java.util.HashMap;
import java.util.Map;

import br.com.depasser.web.resource.Type;

public class StylesheetType extends Type {
	
	private String [] supportedTypes = new String [] {"css", "stylesheet"};
	
	private Map<String, String> attributes = new HashMap<String, String>();

	public StylesheetType() {
		attributes.put("type", "text/css");
		attributes.put("rel", "stylesheet");
	}
	
	@Override
	public String getTagName() {
		return "link";
	}

	@Override
	public String getContentType() {
		return "text/css";
	}

	@Override
	public String[] getSupportedTypes() {
		return supportedTypes;
	}

	@Override
	public String getAttributeName() {
		return "href";
	}

	@Override
	public Map<String, String> getOtherAttributes() {
		return attributes;
	}
	
}