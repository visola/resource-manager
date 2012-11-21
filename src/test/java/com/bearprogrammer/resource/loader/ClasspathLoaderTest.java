package com.bearprogrammer.resource.loader;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ClasspathLoaderTest {
	
	private static final String RESOURCE = "test/somefile.txt";
	private static final String RESOURCE_CONTENT = "This is a file.";
	
	private ClasspathLoader loader;
	
	@Before
	public void init() {
		loader = new ClasspathLoader();
	}
	
	@Test
	public void shouldFindExistingResource() throws Exception {
		Assert.assertTrue("Should find existing resource.", loader.hasResource(RESOURCE));
	}
	
	@Test
	public void shouldLoadResourceContent() throws Exception {
		String content = loader.loadResource(RESOURCE);
		Assert.assertEquals("Should load resource content correctly.", RESOURCE_CONTENT, content);
	}
	
	@Test
	public void shouldReturnLastModifiedIfResourceIsInFilesystem() throws Exception {
		Assert.assertNotNull("Should return last modified if resource is in file system.", loader.lastUpdated(RESOURCE));
	}
	
}
