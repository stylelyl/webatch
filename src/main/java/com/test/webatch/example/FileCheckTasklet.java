package com.test.webatch.example;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class FileCheckTasklet implements Tasklet, InitializingBean {
	@Autowired
	private Resource resource;

	@Override
	public void afterPropertiesSet() throws Exception {
		// Assert.notNull(directory, "directory must be set");
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File file = resource.getFile();
		// Assert.state(dir.isDirectory());

		if (!file.exists()) {
			throw new UnexpectedJobExecutionException("no file "
					+ file.getPath());
		} else {
			System.out.println(file.getPath() + " is exists!");
		}
		return RepeatStatus.FINISHED;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource directory) {
		this.resource = directory;
	}

}
