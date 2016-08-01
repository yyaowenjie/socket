package com.sq.job.trigger.component;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.sq.job.trigger.domain.ScheduleJob;
import com.sq.util.SpringUtils;

/**
 * Task������
 * 
 * @User yaowenjie
 * @Date 2016-7-20
 * @Time ����1:55:05
 */
@Component
public class TaskUtils {
	
	private static final Logger log = LoggerFactory.getLogger(TaskUtils.class);
	
	/**
	 * ͨ���������scheduleJob�ж���ķ���
	 * 
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		// springId��Ϊ���Ȱ�springId����bean
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringUtils.getBean(scheduleJob.getSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				log.error("���� newInstance ����");
			}

		}
		if (object == null) {
			log.error("�������� = [" + scheduleJob.getJobName()
					+ "]---------------δ�����ɹ��������Ƿ�������ȷ������");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
		} catch (NoSuchMethodException e) {
			log.error("�������� = [" + scheduleJob.getJobName()
					+ "]---------------δ�����ɹ������������ô��󣡣���");
		} catch (SecurityException e) {
			log.error("SecurityException",e);
		}
		if (method != null) {
			try {
				method.invoke(object);
			} catch (Exception e) {
				log.error("method.invoke(object) �����쳣��");
			} 
		}

	}
}