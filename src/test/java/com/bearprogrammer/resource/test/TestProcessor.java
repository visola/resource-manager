package com.bearprogrammer.resource.test;

import com.bearprogrammer.resource.ProcessingException;
import com.bearprogrammer.resource.Processor;

public class TestProcessor  implements Processor {

	@Override
	public String process(String content) throws ProcessingException {
		return content.replaceAll("Hello", "Good bye");
	}

	@Override
	public boolean supportType(String typeName) {
		return TestType.class.getName().equals(typeName);
	}

	@Override
	public int compareTo(Processor arg0) {
		return 0;
	}
	
}
