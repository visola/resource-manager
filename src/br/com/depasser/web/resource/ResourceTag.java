package br.com.depasser.web.resource;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceTag extends BodyTagSupport {

	private Logger logger = LoggerFactory.getLogger(ResourceTag.class);
	private static final long serialVersionUID = 8844809472214720402L;
	
	private String type;

	@Override
	public int doEndTag() throws JspException {
		if (bodyContent == null) {
			throw new NullPointerException("Body content is null.");
		}
		
		String body = bodyContent.getString().trim();
		if (body.equals("")) {
			throw new IllegalArgumentException("Body is empty.");
		}
		
		String [] splitted = body.split("\n");
		
		if (logger.isDebugEnabled()) {
			logger.debug("Invoked with resources:");
			for (String r : splitted) {
				logger.debug("\t".concat(r.trim()));
			}
		}
		
		// Build the URL
		StringBuilder url = new StringBuilder();
		url.append(pageContext.getServletContext().getContextPath());
		// TODO - Read this from init-param
		url.append("/resources.do?");
		for (int i = 0; i < splitted.length; i++) {
			String file = splitted[i].trim();
			url.append("file=");
			url.append(file);
			if (i < splitted.length - 1) url.append("&");
		}
		
		ResourceType thisType = null;
		for (ResourceType type : ResourceType.getTypes()) {
			for (String acceptType : type.getSupportedTypes()) {
				if (this.type.equals(acceptType)) {
					thisType = type;
				}
			}
		}
		
		if (thisType == null) {
			throw new IllegalArgumentException("No type defined for: " + this.type);
		}
		
		try { 
			pageContext.getOut().print(thisType.createTag(url.toString()));
		} catch(IOException ioe) {
			throw new JspException(ioe.getMessage());
		} 

		return SKIP_BODY;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}