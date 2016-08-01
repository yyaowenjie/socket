package com.sq.job.trigger.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sq.job.trigger.repository.ScheduleJobRepository;
import com.sq.job.trigger.component.QuartzJobFactory;
import com.sq.job.trigger.component.QuartzJobFactoryDisallowConcurrentExecution;
import com.sq.job.trigger.domain.JobConsts;
import com.sq.job.trigger.domain.ScheduleJob;

/**
 * 初始化Job类,通过init()开始触发
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 下午1:20:57
 */
@Service
public class ScheduleJobService {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleJobService.class);
	
	@Autowired
	private Scheduler scheduler;
	
	@Resource
	private ScheduleJobRepository ScheduleJobRepository;
	
	//@PostConstruct
	public void init() throws Exception {  
		  
        // 这里从数据库中获取任务信息数据  
        List<ScheduleJob> jobList = ScheduleJobRepository.getAll();  
        System.out.println("数据库查询到的Job数量为:"+jobList.size());
        for (ScheduleJob job : jobList) {  
            addJob(job);  
        }  
    } 
	
	/** 
     * 添加任务 
     *  
     * @param scheduleJob 
     * @throws SchedulerException 
     */  
    public void addJob(ScheduleJob job) throws SchedulerException {  
        if (job == null || !JobConsts.STATUS_RUNNING.equals(job.getJobStatus())) {  
            return;  
        }  
        log.debug(scheduler + ".......................................................................................add");  
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());  
  
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
  
        // 不存在，创建一个  
        if (null == trigger) {  
            Class clazz = JobConsts.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;  
  
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();  
  
            jobDetail.getJobDataMap().put("scheduleJob", job);  
  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
  
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();  
  
            scheduler.scheduleJob(jobDetail, trigger);  
        } else {  
            // Trigger已存在，那么更新相应的定时设置  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
  
            // 按新的cronExpression表达式重新构建trigger  
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
  
            // 按新的trigger重新设置job执行  
            scheduler.rescheduleJob(triggerKey, trigger);  
        }  
    }  
}
