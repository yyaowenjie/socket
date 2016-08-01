package com.sq.job.trigger.component;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.sq.job.trigger.domain.ScheduleJob;
import com.sq.util.SpringUtils;

/**
 * Task工具类
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time 下午1:55:05
 */
@Component
public class TaskUtils {
	
	private static final Logger log = LoggerFactory.getLogger(TaskUtils.class);
	
	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		// springId不为空先按springId查找bean
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringUtils.getBean(scheduleJob.getSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				log.error("反射 newInstance 错误！");
			}

		}
		if (object == null) {
			log.error("任务名称 = [" + scheduleJob.getJobName()
					+ "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
		} catch (NoSuchMethodException e) {
			log.error("任务名称 = [" + scheduleJob.getJobName()
					+ "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			log.error("SecurityException",e);
		}
		if (method != null) {
			try {
				method.invoke(object);
			} catch (Exception e) {
				log.error("method.invoke(object) 创建异常！");
			} 
		}

	}
}