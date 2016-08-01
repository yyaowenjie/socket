package com.sq.job.trigger.component;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sq.job.trigger.domain.ScheduleJob;

/**
 * 计划任务执行处 无状态  
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 下午1:52:12
 */
public class QuartzJobFactory implements Job { 
	
	private static final Logger log = LoggerFactory.getLogger(QuartzJobFactory.class);
	
    public void execute(JobExecutionContext context) throws JobExecutionException {  
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");  
        TaskUtils.invokMethod(scheduleJob);  
    }  
}  