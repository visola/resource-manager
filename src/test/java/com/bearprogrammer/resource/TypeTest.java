package com.bearprogrammer.resource;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.bearprogrammer.resource.test.TestProcessor;
import com.bearprogrammer.resource.test.TestType;

public class TypeTest {
	
	private TestType type;
	
	@Before
	public void init() {
		type = new TestType();
	}
	
	@Test
	public void shouldFindProcessorRegisteredAsService() {
		boolean contains = false;
		for (Processor p : type.getProcessors()) {
			if (p instanceof TestProcessor) {
				contains = true;
			}
		}
		
		Assert.assertTrue("Should find processor.", contains);
	}
	
	@Test
	public void shouldNotAddUnsupportedProcessor() {
		Collection<Processor> processors = type.getProcessors();
		Assert.assertEquals("Should only find supported processors.", 1, processors.size());
		
		for (Processor p : processors) {
			if ( !(p instanceof TestProcessor) ) {
				Assert.fail("Should only find supported processors.");
			}
		}
	}
	
	@Test
	public void shouldProcessString() throws ProcessingException {
		String hello = "Hello world!";
		String processed = type.processContent(hello);
		Assert.assertEquals("Should process string.", "Good bye world!", processed);
	}

}
