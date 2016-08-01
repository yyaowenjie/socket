package com.sq.job;

import java.text.SimpleDateFormat;  
import java.util.Calendar;  
  
import org.quartz.Job;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  
  
public class SimpleJob implements Job {  
  
    public void execute(JobExecutionContext context)  
            throws JobExecutionException {  
        // job ������  
        String jobName = context.getJobDetail().getKey().getName();  
          
        // ����ִ�е�ʱ��  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy �� MM �� dd ��  HH ʱ mm �� ss ��");  
        String jobRunTime = dateFormat.format(Calendar.getInstance().getTime());  
          
        // �������ִ�����  
        System.out.println("���� : " + jobName + " ��  " +jobRunTime + " ִ���� ");  
    }  
}  