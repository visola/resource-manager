package com.bearprogrammer.resource;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.TreeSet;

/**
 * A resource type identifies and gives some information about a specific
 * content type. Typical resource type examples are javascript and stylesheets.
 * 
 * @author Vinicius Isola
 */
public abstract class Type {
	
	/**
	 * Store all processors for this specific type.
	 */
	Collection<Processor> processors = null;
	
	/**
	 * Must return the mime type for this type.
	 * 
	 * @return MIME type for this resource type.
	 */
	public abstract String getContentType();
	
	/**
	 * Return a collection containing all the processors available for this type. The collection
	 * will be already sorted in the order they should be called.
	 * 
	 * @return A list with all processors or null if none available.
	 */
	public Collection<Processor> getProcessors() {
		if (processors == null) {
			ServiceLoader<Processor> serviceLoader = ServiceLoader.load(Processor.class);
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
	 * Return information to generate a {@link TypeTag tag} for this specific
	 * type of resource.
	 * 
	 * @return The type tag for this type.
	 */
	public abstract TypeTag getTypeTag();

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