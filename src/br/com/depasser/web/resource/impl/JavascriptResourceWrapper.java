package br.com.depasser.web.resource.impl;

import java.io.File;

import br.com.depasser.web.resource.ResourceType;
import br.com.depasser.web.resource.ResourceWrapper;

public class JavascriptResourceWrapper extends ResourceWrapper {

	public JavascriptResourceWrapper(ResourceType type, File file) {
		super(type, file);
	}

	@Override
	protected void processContent() {
		// TODO - Add compressing
		return;
	}

}