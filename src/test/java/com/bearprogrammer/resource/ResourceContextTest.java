package com.bearprogrammer.resource;

import org.junit.Assert;
import org.junit.Test;

import com.bearprogrammer.resource.test.TestLoader;

public class ResourceContextTest {
	
	@Test
	public void shouldLoadTestContent() throws Exception {
		String content = ResourceContext.getInstance().getResource(TestLoader.IDENTIFIER);
		Assert.assertEquals("Should load test content.", TestLoader.getContent(), content);
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
