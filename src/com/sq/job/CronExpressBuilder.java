package com.sq.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.CronExpression;
import org.springframework.stereotype.Component;

/**
 * 测试Cron表达式是否正确,抛出异常说明有问题
 * 正确显示即将触发的10次时间
 * 
 * @User yaowenjie
 * @Date 2016-07-20
 * @Time 上午10:21:04
 */
@Component
public class CronExpressBuilder {
	
	public static void main(String[] args) {
		try {
			CronExpression exp = new CronExpression("0/10 * * * * ?");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			int i = 0;
			// 循环得到接下来10次的触发时间点，供验证
			while (i < 10) {
				d = exp.getNextValidTimeAfter(d);
				System.out.println(df.format(d));
				i += 1;
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException(
					"CronExpression is invalid,Unexpected end of expression.");
		}
	}
}
