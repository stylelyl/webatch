package com.test.webatch.engine;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Scope;


public class WePartitioner implements Partitioner {
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
		int pageSize = 10;
		int start = 1;
		int end = pageSize;
		for (int i = 1; i <= gridSize; i++) {
			ExecutionContext value = new ExecutionContext();
			System.out.println(Thread.currentThread().getName() + "[" + start
					+ "-" + end + "]");
			value.putInt("start", start);
			value.putInt("end", end);
			value.putInt("page", i);//current page
			value.putInt("pageSize", pageSize);
			//value.putString("name", "Thread" + i);
			result.put("part-" + i, value);
			start = end + 1;
			end += pageSize;
		}
		return result;
	}
}