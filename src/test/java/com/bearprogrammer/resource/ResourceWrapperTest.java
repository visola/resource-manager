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
	private ResourceWrapper wrapper = null;
	
	@Before
	public void init() {
		// Make sure no processor will affect this test
		Deencapsulation.setField(TestType.class, "processors", new HashSet<Processor>());
		
		// Prepare content to be loaded
		TestLoader.generateNewContent();
		
		// Instantiate wrapper
		wrapper = new ResourceWrapper(TestLoader.IDENTIFIER, type, loader);
	}
	
	@Test
	public void shouldLoadContent() throws Exception {
		String content = wrapper.getContent();
		Assert.assertEquals("Should load content.", TestLoader.content, content);
	}

	@Test
	public void shouldReloadChangedContent() throws Exception  {
		shouldLoadContent();
		
		// Change content
		TestLoader.generateNewContent();
		String newContent = wrapper.getContent();
		
		Assert.assertEquals("Should reload content.", TestLoader.content, newContent);
	}
	
	@Test
	public void shouldNotReloadIfContentDidntChange() throws Exception {
		shouldLoadContent();
		
		String oldContent = TestLoader.content;
		
		String content = wrapper.getContent();
		Assert.assertEquals("Should not load new content if last modified didn't change.", oldContent, content);
	}
	
}
