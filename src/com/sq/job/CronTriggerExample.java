package com.sq.job;

import static org.quartz.CronScheduleBuilder.cronSchedule;  
import static org.quartz.JobBuilder.newJob;  
import static org.quartz.TriggerBuilder.newTrigger;  
  
import java.text.SimpleDateFormat;  
import java.util.Date;  
  
import org.quartz.CronTrigger;  
import org.quartz.JobDetail;  
import org.quartz.Scheduler;  
import org.quartz.SchedulerFactory;  
import org.quartz.SchedulerMetaData;  
import org.quartz.impl.StdSchedulerFactory;  

/**
 * 控制Quartz定时时间以及数量
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 上午11:26:05
 */
public class CronTriggerExample {  
  
    public static void main(String[] args) throws Exception {  
        CronTriggerExample example = new CronTriggerExample();  
        example.run();  
    }  
  
    public void run() throws Exception {  
        // 日期格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat(  
                "yyyy 年 MM 月 dd 日  HH 时 mm 分 ss 秒");  
  
        SchedulerFactory sf = new StdSchedulerFactory();  
        Scheduler sched = sf.getScheduler();  
        System.out.println("--------------- 初始化 -------------------");  
  
        // job1 每20s运行一次  
        JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1")  
                .build();  
  
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")  
                .withSchedule(cronSchedule("0/20 * * * * ?")).build();  
  
        Date ft = sched.scheduleJob(job, trigger);  
        System.out.println(job.getKey().getName() + " 将在: "  
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "  
                + trigger.getCronExpression() + "  (含义:每20s运行一次)");  
  
        // job2 偶数分钟的第15秒运行  
        job = newJob(SimpleJob.class).withIdentity("job2", "group1").build();  
        trigger = newTrigger().withIdentity("trigger2", "group1")  
                .withSchedule(cronSchedule("15 0/2 * * * ?")).build();  
  
        ft = sched.scheduleJob(job, trigger);  
        System.out.println(job.getKey().getName() + " 将在: "  
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "  
                + trigger.getCronExpression() + "  (含义:偶数分钟的第15秒运行)");  
  
        sched.start();  
  
        System.out.println("------- 开始调度 (调用.start()方法) ----------------");  
        System.out.println("------- 等待5分钟,给任务的调度留出时间  ... ------------");  
        try {  
            Thread.sleep(300L * 1000L);  
        } catch (Exception e) {  
        	
        }  
  
        sched.shutdown(true);  
        System.out.println("------- 时间到--调度已关闭 ---------------------");  
  
        // 显示一下 已经执行的任务信息  
        SchedulerMetaData metaData = sched.getMetaData();  
        System.out.println("~~~~~~~~~~  执行了 "  
                + metaData.getNumberOfJobsExecuted() + " 个 jobs.");  
    }  
}  