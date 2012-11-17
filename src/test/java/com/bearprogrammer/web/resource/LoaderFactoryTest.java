package com.bearprogrammer.web.resource;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class LoaderFactoryTest {
	
	@Test
	public void shouldLoadLoaderRegisteredAsService() {
		Loader[] loaders = LoaderFactory.getLoaders();
		
		boolean contains = false;
		for (Loader loader : loaders) {
			if (loader instanceof TestLoader) {
				contains = true;
				break;
			}
		}
		
		Assert.assertTrue("Should load loader registered as service.", contains);
	}
	
	@Test
	public void shouldFindLoaderByIdentifier() throws IOException {
		Loader loader = LoaderFactory.getLoader(TestLoader.IDENTIFIER);
		Assert.assertNotNull("Should find loader by identifier.", loader);
	}
	
	@Test
	public void shouldReturnNullForNonExistingIdentifier() throws IOException {
		Loader loader = LoaderFactory.getLoader("This is a resource that does not exist.");
		Assert.assertNull("Should return null for non-existing identifier.", loader);
	}

}
