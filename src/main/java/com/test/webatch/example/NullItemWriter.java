package com.test.webatch.example;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class NullItemWriter<T> implements ItemWriter<T> {

	@Override
	public void write(List<? extends T> items) throws Exception {
	}

}
