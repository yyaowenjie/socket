package com.sq.job.trigger.repository;

import java.util.List;

import com.sq.job.trigger.domain.ScheduleJob;

/**
 * ScheduleJob�ֿ�
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����2:03:50
 */
public interface ScheduleJobRepository {
	
	List<ScheduleJob> getAll();
	
}
