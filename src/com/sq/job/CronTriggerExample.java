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
 * ����Quartz��ʱʱ���Լ�����
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����11:26:05
 */
public class CronTriggerExample {  
  
    public static void main(String[] args) throws Exception {  
        CronTriggerExample example = new CronTriggerExample();  
        example.run();  
    }  
  
    public void run() throws Exception {  
        // ���ڸ�ʽ��
        SimpleDateFormat dateFormat = new SimpleDateFormat(  
                "yyyy �� MM �� dd ��  HH ʱ mm �� ss ��");  
  
        SchedulerFactory sf = new StdSchedulerFactory();  
        Scheduler sched = sf.getScheduler();  
        System.out.println("--------------- ��ʼ�� -------------------");  
  
        // job1 ÿ20s����һ��  
        JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1")  
                .build();  
  
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")  
                .withSchedule(cronSchedule("0/20 * * * * ?")).build();  
  
        Date ft = sched.scheduleJob(job, trigger);  
        System.out.println(job.getKey().getName() + " ����: "  
                + dateFormat.format(ft) + " ���� . ���һ���Cron���ʽ : "  
                + trigger.getCronExpression() + "  (����:ÿ20s����һ��)");  
  
        // job2 ż�����ӵĵ�15������  
        job = newJob(SimpleJob.class).withIdentity("job2", "group1").build();  
        trigger = newTrigger().withIdentity("trigger2", "group1")  
                .withSchedule(cronSchedule("15 0/2 * * * ?")).build();  
  
        ft = sched.scheduleJob(job, trigger);  
        System.out.println(job.getKey().getName() + " ����: "  
                + dateFormat.format(ft) + " ���� . ���һ���Cron���ʽ : "  
                + trigger.getCronExpression() + "  (����:ż�����ӵĵ�15������)");  
  
        sched.start();  
  
        System.out.println("------- ��ʼ���� (����.start()����) ----------------");  
        System.out.println("------- �ȴ�5����,������ĵ�������ʱ��  ... ------------");  
        try {  
            Thread.sleep(300L * 1000L);  
        } catch (Exception e) {  
        	
        }  
  
        sched.shutdown(true);  
        System.out.println("------- ʱ�䵽--�����ѹر� ---------------------");  
  
        // ��ʾһ�� �Ѿ�ִ�е�������Ϣ  
        SchedulerMetaData metaData = sched.getMetaData();  
        System.out.println("~~~~~~~~~~  ִ���� "  
                + metaData.getNumberOfJobsExecuted() + " �� jobs.");  
    }  
}  