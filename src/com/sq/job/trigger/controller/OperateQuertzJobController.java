package com.sq.job.trigger.controller;

import java.util.ArrayList;
import java.util.List;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.sq.job.trigger.domain.ScheduleJob;
import com.sq.job.trigger.service.JobQuertzService;

/**
 * �������������Job
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����3:11:23
 */
@Controller
public class OperateQuertzJobController {
	
	private static final Logger log = LoggerFactory.getLogger(OperateQuertzJobController.class);
	
	@Autowired
	private JobQuertzService jobQuertzService;
	
	/**
	 * �������id
	 *         1.��ͣJob
	 *         2.�ָ�Job
	 *         3.ɾ��Job
	 *         4.��������Job
	 *         5.�޸ĸ�Job��Cron
	 *         6.ȫ������
	 *         7.ȫ����ͣ
	 *         8.ȫ���군
	 * @return
	 * @throws SchedulerException       
	 */  
	@ResponseBody
	@RequestMapping("job/operdateJob.do")
	public String operdateJob(@RequestParam Integer id) throws SchedulerException {
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setJobGroup("group3");
		scheduleJob.setJobName("job3");
		switch (id) {
		case 1:
			jobQuertzService.pauseJob(scheduleJob);
			break;
		case 2:
			jobQuertzService.resumeJob(scheduleJob);
			break;
		case 3:
			jobQuertzService.deleteJob(scheduleJob);
			break;
		case 4:
			jobQuertzService.runAJobNow(scheduleJob);
			break;
		case 5:
			scheduleJob.setCronExpression("0 */1 * * * ?");
			jobQuertzService.updateJobCron(scheduleJob);
			break;
		case 6:
			jobQuertzService.startCompleteJob();
			break;
		case 7:
			jobQuertzService.closeCompleteJob();
			break;
		case 8:
			jobQuertzService.shutdownCompleteJob();
			break;
		default:
			break;
		}
		return "�����ɹ���";
	}
	
	/**
	 * ��ʾ���е�Job����û���е�
	 * @return
	 * @throws SchedulerException
	 */
	@ResponseBody
	@RequestMapping("job/showAllJob.do")
	public String showAllJob() throws SchedulerException{
		List<ScheduleJob> list = jobQuertzService.getAllJob();
		return JSON.toJSONString(list);
	}
	
	/**
	 * ��ʾ�������е�Job
	 * @return
	 * @throws SchedulerException
	 */
	@ResponseBody
	@RequestMapping("job/showRunningJob.do")
	public String showRunningJob() throws SchedulerException{
		List<ScheduleJob> list = new ArrayList<ScheduleJob>();
		list = jobQuertzService.getRunningJob();
		return JSON.toJSONString(list);
	}
}
