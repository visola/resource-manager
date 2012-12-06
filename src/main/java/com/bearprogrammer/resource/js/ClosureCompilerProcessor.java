package com.bearprogrammer.resource.js;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bearprogrammer.resource.ProcessingException;
import com.bearprogrammer.resource.Processor;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;

public class ClosureCompilerProcessor implements Processor {
	
	static final Logger LOGGER = LoggerFactory.getLogger(ClosureCompilerProcessor.class);

	@Override
	public int compareTo(Processor another) {
		return 0;
	}

	@Override
	public String process(String content) throws ProcessingException {
		LOGGER.trace("Compressing content: {}", content);
		StringReader in = new StringReader(content);
		try {
	        SourceFile source = SourceFile.builder().buildFromReader("processor", in);
	        
	        List<SourceFile> externs = Collections.emptyList();
	        List<SourceFile> inputs = Arrays.asList( new SourceFile[] { source } );
			
	        com.google.javascript.jscomp.Compiler.setLoggingLevel( Level.OFF );
	        com.google.javascript.jscomp.Compiler compiler = new com.google.javascript.jscomp.Compiler();
	        
	        CompilerOptions options = new CompilerOptions();
	        
	        CompilationLevel level = CompilationLevel.SIMPLE_OPTIMIZATIONS;
	        level.setOptionsForCompilationLevel(options);
	        
	        compiler.compile(externs, inputs, options);
			
			return compiler.toSource();
		} catch (IOException ioe) {
			throw new ProcessingException("Error while compressing javascript.", ioe);
		}
	}

	@Override
	public boolean supportType(String typeName) {
		return JavascriptType.class.getName().equals(typeName);
	}

}
