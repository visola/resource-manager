package com.bearprogrammer.resource;

import java.util.HashSet;

import mockit.Deencapsulation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bearprogrammer.resource.test.TestLoader;
import com.bearprogrammer.resource.test.TestType;

public class ResourceWrapperTest {

	private TestLoader loader = new TestLoader();
	private TestType type = new TestType();
	
	@Test
	public void shouldLoadContent() throws Exception {
		ResourceWrapper wrapper = new ResourceWrapper(TestLoader.IDENTIFIER, type, loader);
		String content = wrapper.getContent();
		
		Assert.assertEquals("Should load content.", TestLoader.getContent(), content);
	}

	@Test
	public void shouldReloadChangedContent() throws Exception  {
		ResourceWrapper wrapper = new ResourceWrapper(TestLoader.IDENTIFIER, type, loader);
		String content = wrapper.getContent();
		
		Assert.assertEquals("Should load content.", TestLoader.getContent(), content);
		
		// Change content
		TestLoader.generateNewContent();
		String newContent = wrapper.getContent();
		
		Assert.assertEquals("Should reload content.", TestLoader.getContent(), newContent);
	}
	
	@Before
	public void unloadProcessors() {
		// Make sure no processor will affect this test
		Deencapsulation.setField(TestType.class, "processors", new HashSet<Processor>());
	}
	
}
