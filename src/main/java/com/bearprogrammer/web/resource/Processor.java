package com.bearprogrammer.web.resource;

/**
 * <p>
 * A resource processor can be used to process a specified resource type. Any
 * kind of processing can be done, including: compressing and obfuscation.
 * </p>
 * 
 * <p>
 * Each {@link Type} can have one or more processors that can be invoked
 * in a specified order. The order
 * </p>
 * 
 * @author Vinicius Isola (viniciusisola@gmail.com)
 */
public interface Processor {

	/**
	 * Do the processing.
	 * 
	 * @param content
	 *            The content to be processed.
	 * @return The processed content.
	 * @throws Exception
	 *             If any error occur while processing the content.
	 */
	public String process(String content) throws Exception;

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