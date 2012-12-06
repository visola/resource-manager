package com.bearprogrammer.resource.css;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bearprogrammer.resource.ProcessingException;
import com.bearprogrammer.resource.Processor;
import com.yahoo.platform.yui.compressor.CssCompressor;

public class YUIStylesheetProcessor implements Processor {
	
	static final Logger LOGGER = LoggerFactory.getLogger(YUIStylesheetProcessor.class);

	@Override
	public int compareTo(Processor another) {
		return 0;
	}

	@Override
	public String process(String content) throws ProcessingException {
		LOGGER.trace("Compressing content: {}", content);
		StringReader in = new StringReader(content);
		try {
			CssCompressor compressor = new CssCompressor(in);
			StringWriter out = new StringWriter();
			compressor.compress(out,
					-1); // no line break for long lines 
			
			return out.toString();
		} catch (IOException ioe) {
			throw new ProcessingException("Error while compressing javascript.", ioe);
		}
	}

	@Override
	public boolean supportType(String typeName) {
		return StylesheetType.class.getName().equals(typeName);
	}

}
