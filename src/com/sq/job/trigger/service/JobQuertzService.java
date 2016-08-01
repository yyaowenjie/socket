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
 * 操作Job业务类
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 下午2:01:08
 */
@Service
public class JobQuertzService {
	
	private static final Logger log = LoggerFactory.getLogger(JobQuertzService.class);
	
	@Autowired
	private Scheduler scheduler;
	
   /**  
    * 获取所有计划中的任务列表  
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
               job.setDescription("定时器:" + trigger.getKey());  
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
    * 获取所有正在运行的job,该方法获取的时间
    * 必须是该Job运行的时间，否则抓取不到
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
           job.setDescription("定时器:" + trigger.getKey());
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
    * 开启所有的Job
    * 
    * @throws SchedulerException
    */
   public void startCompleteJob() throws SchedulerException{
	   scheduler.resumeAll();
   }
   
   /**
    * 关闭所有的Job，可以重启
    * 
    * @throws SchedulerException
    */
   public void closeCompleteJob() throws SchedulerException{
	   scheduler.pauseAll();
   }
   
   /**
    * 关闭所有的Job,注意关闭后无法重启
    * 
    * @throws SchedulerException
    */
   public void shutdownCompleteJob() throws SchedulerException{
	   scheduler.shutdown();
   }
   
   /** 
    * 暂停一个job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.pauseJob(jobKey); 
       log.error("任务 = [" + scheduleJob.toString()+ "]-----暂停！！！");
   }  
 
   /** 
    * 恢复一个job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.resumeJob(jobKey);  
       log.error("任务 = [" + scheduleJob.toString()+ "]-----被恢复！！！");
   }  
 
   /** 
    * 删除一个job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.deleteJob(jobKey);  
       log.error("任务 = [" + scheduleJob.toString()+ "]-----被删除！！！");
   }  
 
   /** 
    * 立即执行job 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {  
       JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
       scheduler.triggerJob(jobKey);
       log.error("任务 = [" + scheduleJob.toString()+ "]-----执行一次！！！");
   }  
 
   /** 
    * 更新job时间表达式 
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
       log.error("任务 = [" + scheduleJob.toString()+ "]-----时间表达式被修改为" + scheduleJob.getCronExpression());
   }  
}
