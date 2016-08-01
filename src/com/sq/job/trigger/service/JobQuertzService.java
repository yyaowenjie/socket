package com.sq.job.trigger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sq.job.trigger.domain.ScheduleJob;

/**
 * ����Jobҵ����
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����2:01:08
 */
@Service
public class JobQuertzService {
	
	private static final Logger log = LoggerFactory.getLogger(JobQuertzService.class);
	
	@Autowired
	private Scheduler scheduler;
	
   /**  
    * ��ȡ���мƻ��е������б�  
    *   
    * @return  
    * @throws SchedulerException  
    */  
   public List<ScheduleJob> getAllJob() throws SchedulerException {  
       GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();  
       Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);  
	   List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();  
       for (JobKey jobKey : jobKeys) {  
           List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);  
           for (Trigger trigger : triggers) {  
               ScheduleJob job = new ScheduleJob();  
               job.setJobName(jobKey.getName());  
               job.setJobGroup(jobKey.getGroup());  
               job.setDescription("��ʱ��:" + trigger.getKey());  
               Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
               job.setJobStatus(triggerState.name());  
               if (trigger instanceof CronTrigger) {  
                   CronTrigger cronTrigger = (CronTrigger) trigger;  
                   String cronExpression = cronTrigger.getCronExpression();  
                   job.setCronExpression(cronExpression);  
               }  
               jobList.add(job);  
           }  
       }  
       return jobList;  
   }  
 
   /** 
    * ��ȡ�����������е�job,�÷�����ȡ��ʱ��
    * �����Ǹ�Job���е�ʱ�䣬����ץȡ����
    *  
    * @return 
    * @throws SchedulerException 
    */  
   public List<ScheduleJob> getRunningJob() throws SchedulerException {	   
       List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
       List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
       for (JobExecutionContext executingJob : executingJobs) {
           ScheduleJob job = new ScheduleJob();
           JobDetail jobDetail = executingJob.getJobDetail();
           JobKey jobKey = jobDetail.getKey();
           Trigger trigger = executingJob.getTrigger();
           job.setJobName(jobKey.getName());
           job.setJobGroup(jobKey.getGroup());
           job.setDescription("��ʱ��:" + trigger.getKey());
           Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
           job.setJobStatus(triggerState.name());
           if (trigger instanceof CronTrigger) {
               CronTrigger cronTrigger = (CronTrigger) trigger;
               String cronExpression = cronTrigger.getCronExpression();
               job.setCronExpression(cronExpression);
           }
           jobList.add(job);
       }
       
       return jobList;
   } 
    
   /**
    * �������е�Job
    * 
    * @throws SchedulerException
    */
   public void startCompleteJob() throws SchedulerException{
	   scheduler.resumeAll();
   }
   
   /**
    * �ر����е�Job����������
    * 
    * @throws SchedulerException
    */
   public void closeCompleteJob() throws SchedulerException{
	   scheduler.pauseAll();
   }
   
   /**
    * �ر����е�Job,ע��رպ��޷�����
    * 
    * @throws SchedulerException
    */
   public void shutdownCompleteJob() throws SchedulerException{
	   scheduler.shutdown();
   }
   
   /** 
    * ��ͣһ��job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.pauseJob(jobKey); 
       log.error("���� = [" + scheduleJob.toString()+ "]-----��ͣ������");
   }  
 
   /** 
    * �ָ�һ��job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.resumeJob(jobKey);  
       log.error("���� = [" + scheduleJob.toString()+ "]-----���ָ�������");
   }  
 
   /** 
    * ɾ��һ��job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.deleteJob(jobKey);  
       log.error("���� = [" + scheduleJob.toString()+ "]-----��ɾ��������");
   }  
 
   /** 
    * ����ִ��job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.triggerJob(jobKey);
       log.error("���� = [" + scheduleJob.toString()+ "]-----ִ��һ�Σ�����");
   }  
 
   /** 
    * ����jobʱ����ʽ 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {  
      
	   TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
 
       CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
 
       CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());  
 
       trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
 
       scheduler.rescheduleJob(triggerKey, trigger);  
       log.error("���� = [" + scheduleJob.toString()+ "]-----ʱ����ʽ���޸�Ϊ" + scheduleJob.getCronExpression());
   }  
}
