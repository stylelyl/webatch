package com.test.webatch;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.webatch.dao.Page;
import com.test.webatch.dao.TmCardInfoDao;
import com.test.webatch.domain.TmCardInfo;

public class App {

	public static void main(String[] args) {
		System.out.println("start...");
		String springConfig = "launch-context.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(
				springConfig);
		
		Page<TmCardInfo> page = new Page<TmCardInfo>();
		page.setPageNumber(5);
		page.setPageSize(10);
		TmCardInfoDao tmCardInfoDao = (TmCardInfoDao)context.getBean("tmCardInfoDao");
		tmCardInfoDao.queryForPage(TmCardInfo.class.getCanonicalName(), null, page);
		List<TmCardInfo> list = page.getRows();
		for(TmCardInfo c:list){
			System.out.println(c);
		}
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("webatch_job_ex3");

		JobParameters params = new JobParametersBuilder().addDate("batch.date",
				new Date()).toJobParameters();

		try {
			if(1==1)
			jobLauncher.run(job, params);
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("end...");
	}
}
