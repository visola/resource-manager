package br.com.depasser.web.resource.impl;

import java.io.File;

import javax.servlet.ServletConfig;

import br.com.depasser.web.resource.ResourceServlet;
import br.com.depasser.web.resource.ResourceType;
import br.com.depasser.web.resource.ResourceWrapper;

public class JavascriptResourceType extends ResourceType {
	
	public static final String DEFAULT_BASE_DIRECTORY = "script";
	public static final String INIT_PARAM = "br.com.depasser.web.resource.javascript";

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
		tag.append("<script src=\"");
		tag.append(path);
		tag.append("\"></script>");
		return tag.toString();
	}

	@Override
	public String getContentType() {
		return "text/javascript";
	}

	@Override
	public String[] getSupportedTypes() {
		return new String [] {"js", "javascript", "script"};
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
		return new JavascriptResourceWrapper(this, f);
	}

}
