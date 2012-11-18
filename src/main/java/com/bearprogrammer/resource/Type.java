package com.bearprogrammer.resource;

import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeSet;

/**
 * A resource type identifies and gives some information about a specific
 * content type. Typical resource type examples are javascript and stylesheets.
 * 
 * @author Vinicius Isola
 */
public abstract class Type {
	
	private static Collection<Processor> processors = null;
	
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
	 * Return a collection containing all the processors available for this type. The collection
	 * will be already sorted in the order they should be called.
	 * 
	 * @return A list with all processors or null if none available.
	 */
	public Collection<Processor> getProcessors() {
		ServiceLoader<Processor> serviceLoader = ServiceLoader.load(Processor.class);
		if (processors == null) {
			processors = new TreeSet<Processor>();
			for (Processor processor : serviceLoader) {
				if (processor.supportType(this.getClass().getName())) {
					processors.add(processor);
				}
			}
		}
		return processors;
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
	
	/**
	 * Process a content of this type.
	 * 
	 * @param content
	 *            The content to be processed.
	 * @return The processed content.
	 * @throws ProcessingException
	 *             If a problem happens while processing the content.
	 */
	public String processContent(String content) throws ProcessingException {
		Collection<Processor> processors = getProcessors();
		if (processors != null) {
			for (Processor processor : processors) {
				content = processor.process(content);
			}
		}
		return content;
	}

}