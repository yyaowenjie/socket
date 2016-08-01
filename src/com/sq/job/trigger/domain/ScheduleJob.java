package com.sq.job.trigger.domain;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ��ʱ��ʵ����
 * ��ʵ���������ݿ��еı��Ӧ�������ݿ��д洢����ƻ�����
 * ע�⣺jobName �� groupName�����Ӧ����Ψһ�ġ�
 * beanClass springId������һ��
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����1:17:04
 */
@Entity
@Table(name = "t_scheduleJob")
public class ScheduleJob {  
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
    private Long id;  
  
    private Calendar createTime;  
    
    private Calendar updateTime;  
    /** 
     * �������� 
     */  
    private String jobName;  
    /** 
     * ������� 
     */  
    private String jobGroup;  
    /** 
     * ����״̬ �Ƿ��������� 
     */  
    private String jobStatus;  
    /** 
     * cron���ʽ 
     */  
    private String cronExpression;  
    /** 
     * ���� 
     */  
    private String description;  
    /** 
     * ����ִ��ʱ�����ĸ���ķ��� ����+���� 
     */  
    private String beanClass;  
    /** 
     * �����Ƿ���״̬ 
     */  
    private String isConcurrent;  
    /** 
     * spring bean 
     */  
    private String springId;  
    /** 
     * ������õķ����� 
     */  
    private String methodName;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public Calendar getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public String getIsConcurrent() {
		return isConcurrent;
	}
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	public String getSpringId() {
		return springId;
	}
	public void setSpringId(String springId) {
		this.springId = springId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	@Override
	public String toString() {
		return "ScheduleJob [" + jobName + jobGroup
				+ ", beanClass=" + beanClass + "." + methodName
				+ "]";
	}   
}  