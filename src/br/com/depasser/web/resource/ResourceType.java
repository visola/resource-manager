package br.com.depasser.web.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ResourceType {
	
	private static Logger logger = LoggerFactory.getLogger(ResourceType.class);
	private static ResourceType [] types = null;

	/**
	 * <p>
	 * Return the base directory inside the web application to find this type of
	 * resource. If this can be configured through a initialization parameter in
	 * the web.xml file, you can use @{link
	 * {@link ResourceServlet#getInstance()} to get the servlet instance and
	 * through it the servlet context or configuration.
	 * </p>
	 * 
	 * <p>
	 * This path should be a complete path to the directory where to find this
	 * type of files.
	 * </p>
	 * 
	 * @return Path to the base directory to find resources of this type.
	 */
	public abstract String getBaseDirectory();
	
	/**
	 * Return all {@link ResourceType}s available in the class path.
	 * 
	 * @return Return all types loaded by a <code>java.util.ServiceLoader</code>.
	 */
	public synchronized static ResourceType [] getTypes () {
		if (types == null) {
			List<ResourceType> typeList = new ArrayList<ResourceType>();
			
			ServiceLoader<ResourceType> loader = ServiceLoader.load(ResourceType.class);
			for (ResourceType type : loader) {
				logger.debug("ResourceType found: " + type.getClass().getName());
				typeList.add(type);
			}
			types = typeList.toArray(new ResourceType[typeList.size()]);
		}
		return types;
	}
	
	/**
	 * Return the current directory for this application.
	 * 
	 * @return Current directory.
	 */
	public String getCurrentDirectory () {
		return ResourceServlet.getInstance().getServletContext().getRealPath(".");
	}

	/**
	 * Create a <code>String</code> that represents a tag that could be added to
	 * a HTML and sent to a browser to load the resource of this type. If a
	 * javascript file for example, this would be:
	 * <code>&lt;script src="{path}"&gt;&lt;/script&gt;</code>.
	 * 
	 * @param path
	 *            Path to be used in a <code>src</code> or <code>href</code>
	 *            attributes.
	 * @return The full string that can be added to a HTML page to represent a
	 *         tag that would load the specified resource for this type.
	 */
	public abstract String createTag(String path);

	/**
	 * Must return the mime type for this content.
	 * 
	 * @return MIME type for this content.
	 */
	public abstract String getContentType();

	/**
	 * A list of strings that the user could use to reach this specified
	 * resource type. For a javascript file, for example, this could be:
	 * javascript, script, js, json, etc.
	 * 
	 * @return 
	 */
	public abstract String[] getSupportedTypes();

	/**
	 * Wrap a specified file into a {@link ResourceWrapper} of this type.
	 * 
	 * @param path
	 *            Path to the file.
	 * @return A wrapper for the specified file.
	 */
	public abstract ResourceWrapper wrapContent(String path);

}