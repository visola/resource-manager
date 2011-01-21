package br.com.depasser.web.resource.impl;

import java.io.File;

import javax.servlet.ServletConfig;

import br.com.depasser.web.resource.ResourceServlet;
import br.com.depasser.web.resource.ResourceType;
import br.com.depasser.web.resource.ResourceWrapper;

public class StylesheetResourceType extends ResourceType {
	
	public static final String DEFAULT_BASE_DIRECTORY = "style";
	public static final String INIT_PARAM = "br.com.depasser.web.resource.stylesheet";

	@Override
	public String getBaseDirectory() {
		ServletConfig config = ResourceServlet.getInstance().getServletConfig();
		String initParam = config.getInitParameter(INIT_PARAM);
		if (initParam == null) {
			return DEFAULT_BASE_DIRECTORY;
		}
		return initParam;
	}

	@Override
	public String createTag(String path) {
		StringBuilder tag = new StringBuilder();
		tag.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
		tag.append(path);
		tag.append("\" />");
		return tag.toString();
	}

	@Override
	public String getContentType() {
		return "text/css";
	}

	@Override
	public String[] getSupportedTypes() {
		return new String [] {"css", "style", "stylesheet"};
	}

	@Override
	public ResourceWrapper wrapContent(String path) {
		StringBuilder finalPath = new StringBuilder();
		finalPath.append(getCurrentDirectory());
		finalPath.append("/");
		finalPath.append(getBaseDirectory());
		finalPath.append("/");
		finalPath.append(path);
		File f = new File(finalPath.toString());
		return new StylesheetResourceWrapper(this, f);
	}

}
