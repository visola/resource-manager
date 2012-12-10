package com.bearprogrammer.resource;

import java.util.Map;

public interface TypeTag {

	/**
	 * Return the attribute name that should be used to set the path to the
	 * resource for the HTML tag. Example: javascript uses the "script" tag with
	 * the "src" attribute, so this should return "src".
	 * 
	 * @return The attribute name.
	 */
	public abstract String getAttributeName();

	/**
	 * Return all other attributes that should be added to the HTML tag to
	 * identify a resource of this type. This can include attributes like "type"
	 * and "rel" for a "link" tag, for example.
	 * 
	 * @return A map containing all other attributes and its values or null if
	 *         no other attribute is necessary.
	 */
	public abstract Map<String, String> getOtherAttributes();

	/**
	 * Return the tag name that is used to load resources of this type from an
	 * HTML page. Example: javascript = "script"
	 * 
	 * @return The tag name.
	 */
	public abstract String getTagName();

}
