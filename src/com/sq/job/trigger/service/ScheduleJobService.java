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
 * ��ʼ��Job��,ͨ��init()��ʼ����
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����1:20:57
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
		  
        // ��������ݿ��л�ȡ������Ϣ����  
        List<ScheduleJob> jobList = ScheduleJobRepository.getAll();  
        System.out.println("���ݿ��ѯ����Job����Ϊ:"+jobList.size());
        for (ScheduleJob job : jobList) {  
            addJob(job);  
        }  
    } 
	
	/** 
     * ������� 
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
  
        // �����ڣ�����һ��  
        if (null == trigger) {  
            Class clazz = JobConsts.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;  
  
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();  
  
            jobDetail.getJobDataMap().put("scheduleJob", job);  
  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
  
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();  
  
            scheduler.scheduleJob(jobDetail, trigger);  
        } else {  
            // Trigger�Ѵ��ڣ���ô������Ӧ�Ķ�ʱ����  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
  
            // ���µ�cronExpression���ʽ���¹���trigger  
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
  
            // ���µ�trigger��������jobִ��  
            scheduler.rescheduleJob(triggerKey, trigger);  
        }  
    }  
}
