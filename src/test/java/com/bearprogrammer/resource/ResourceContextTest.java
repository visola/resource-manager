package com.bearprogrammer.resource;

import org.junit.Assert;
import org.junit.Test;

public class ResourceContextTest {
	
	@Test
	public void shouldLoadTestContent() throws Exception {
		String content = ResourceContext.getInstance().getResource(TestLoader.IDENTIFIER);
		Assert.assertEquals("Should load test content.", TestLoader.CONTENT, content);
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void shouldThrowExceptionIfResourceNotFound() throws Exception {
		ResourceContext.getInstance().getResource("this resource does not exist.test");
	}
	
	@Test(expected=UnknownTypeException.class)
	public void shouldThrowExceptionForUnknownType() throws Exception {
		ResourceContext.getInstance().getResource("this resource does not exist.weird");
	}

}
