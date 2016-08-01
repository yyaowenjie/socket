package com.sq.job.trigger.component;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sq.job.trigger.domain.ScheduleJob;

/**
 * ��һ������һ��ִ�в����´���תʱ��ȴ��ķ���ִ������ִ����һ�β���
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����1:52:30
 */
@DisallowConcurrentExecution 
//������ͬʱִ��ͬ�������ע��
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(QuartzJobFactoryDisallowConcurrentExecution.class);
	
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
				.get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);

	}
}