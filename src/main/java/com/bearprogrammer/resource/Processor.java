package com.bearprogrammer.resource;

import java.util.Comparator;

/**
 * <p>
 * A resource processor can be used to process a specified resource type. Any
 * kind of processing can be done, including: compressing and obfuscation.
 * </p>
 * 
 * <p>
 * Each {@link Type} can have one or more processors that can be invoked in a
 * specified order. The order to execute the processors will be determined by
 * the implementation of the {@link Comparator#compare(Object, Object) compare}
 * method.
 * </p>
 * 
 * @author Vinicius Isola
 */
public interface Processor extends Comparator<Processor> {

	/**
	 * Do the processing.
	 * 
	 * @param content
	 *            The content to be processed.
	 * @return The processed content.
	 * @throws ProcessingException
	 *             If any error occur while processing the content.
	 */
	public String process(String content) throws ProcessingException;

	/**
	 * Check if this processor supports the specified type.
	 * 
	 * @param typeName
	 *            Name of the type to check for.
	 * @return True if this processor can be used to process resources of this
	 *         type.
	 */
	public boolean supportType(String typeName); 
	
}