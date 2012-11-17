package com.bearprogrammer.web.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * A resource type identifies and gives some information about a specific
 * content type. Typical resource type examples are javascript and stylesheets.
 * 
 * @author Vinicius Isola (viniciusisola@gmail.com)
 */
public abstract class Type {
	
	/**
	 * Return the attribute name that should be used to set the path to the
	 * resource for the HTML tag. Example: javascript uses the "script" tag with
	 * the "src" attribute, so this should return "src".
	 * 
	 * @return The attribute name.
	 */
	public abstract String getAttributeName();
	
	/**
	 * Must return the mime type for this type.
	 * 
	 * @return MIME type for this resource type.
	 */
	public abstract String getContentType();
	
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
	 * Return a list containing all the processors available for this type. They
	 * must be sorted in the order the content should be handled by each one.
	 * 
	 * @return A list with all processors or null if none available.
	 */
	public List<Processor> getProcessors() {
		ServiceLoader<Processor> serviceLoader = ServiceLoader.load(Processor.class);
		List<Processor> result = new ArrayList<Processor>();
		for (Processor processor : serviceLoader) {
			if (processor.supportType(this.getClass().getName())) {
				result.add(processor);
			}
		}
		return result;
	}

	/**
	 * A list of strings that the user could use to reach this specified
	 * resource type. For example, for a javascript file this could be:
	 * javascript, script, js, etc.
	 * 
	 * @return
	 */
	public abstract String[] getSupportedTypes();
	
	/**
	 * Return the tag name that is used to load resources of this type
	 * from an HTML page. Example: javascript = "script"
	 * 
	 * @return The tag name.
	 */
	public abstract String getTagName();

}