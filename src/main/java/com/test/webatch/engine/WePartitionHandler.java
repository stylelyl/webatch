package com.test.webatch.engine;

import java.util.Collection;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.StepExecutionSplitter;

public class WePartitionHandler implements PartitionHandler{

	@Override
	public Collection<StepExecution> handle(StepExecutionSplitter stepSplitter,
			StepExecution stepExecution) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
