package com.sq.job.trigger.component;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sq.job.trigger.domain.ScheduleJob;

/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 下午1:52:30
 */
@DisallowConcurrentExecution 
//不允许同时执行同个任务的注解
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(QuartzJobFactoryDisallowConcurrentExecution.class);
	
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
				.get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);

	}
}