package br.com.depasser.web.resource.impl;

import java.io.File;

import br.com.depasser.web.resource.ResourceType;
import br.com.depasser.web.resource.ResourceWrapper;

public class StylesheetResourceWrapper extends ResourceWrapper {

	public StylesheetResourceWrapper(ResourceType type, File file) {
		super(type, file);
	}

	@Override
	protected void processContent() {
		// TODO - Add compressing
		return;
	}

}