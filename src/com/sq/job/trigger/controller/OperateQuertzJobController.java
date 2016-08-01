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
 * 界面操作触发器Job
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 下午3:11:23
 */
@Controller
public class OperateQuertzJobController {
	
	private static final Logger log = LoggerFactory.getLogger(OperateQuertzJobController.class);
	
	@Autowired
	private JobQuertzService jobQuertzService;
	
	/**
	 * 传入参数id
	 *         1.暂停Job
	 *         2.恢复Job
	 *         3.删除Job
	 *         4.立即运行Job
	 *         5.修改该Job的Cron
	 *         6.全部启动
	 *         7.全部暂停
	 *         8.全部完蛋
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
		return "操作成功！";
	}
	
	/**
	 * 显示所有的Job包括没运行的
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
	 * 显示所有运行的Job
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
