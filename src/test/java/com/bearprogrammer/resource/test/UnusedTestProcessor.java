package com.bearprogrammer.resource.test;

import com.bearprogrammer.resource.ProcessingException;
import com.bearprogrammer.resource.Processor;

public class UnusedTestProcessor implements Processor {

	@Override
	public int compareTo(Processor another) {
		return 0;
	}

	@Override
	public String process(String content) throws ProcessingException {
		return "";
	}

	@Override
	public boolean supportType(String typeName) {
		return false;
	}

}