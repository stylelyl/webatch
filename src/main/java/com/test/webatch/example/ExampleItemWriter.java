package com.test.webatch.example;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


/**
 * Dummy {@link ItemWriter} which only logs data it receives.
 */
@Component("exwriter")
public class ExampleItemWriter implements ItemWriter<String> {

	private static final Log log = LogFactory.getLog(ExampleItemWriter.class);
	
	public void write(List<? extends String> data) throws Exception {
		log.info(data);
	}

}
