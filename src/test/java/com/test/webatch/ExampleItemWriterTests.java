package com.test.webatch;

import junit.framework.TestCase;

import com.test.webatch.example.ExampleItemWriter;

public class ExampleItemWriterTests extends TestCase {

	private ExampleItemWriter writer = new ExampleItemWriter();
	
	public void testWrite() throws Exception {
		writer.write(null); // nothing bad happens
	}

}
