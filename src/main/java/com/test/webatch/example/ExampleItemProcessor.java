package com.test.webatch.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

/**
 * {@link ItemReader} with hard-coded input data.
 */

@Component("exprocessor")
public class ExampleItemProcessor implements ItemProcessor<String, String> {

	private static final Log log = LogFactory
			.getLog(ExampleItemProcessor.class);

	@Override
	public String process(String s) throws Exception {
		log.info(Thread.currentThread().getName() + " input=" + s + ",out="
				+ System.currentTimeMillis() + " " + s);
		return System.currentTimeMillis() + s;
	}

}
