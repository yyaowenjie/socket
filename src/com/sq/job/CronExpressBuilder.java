package com.sq.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.CronExpression;
import org.springframework.stereotype.Component;

/**
 * ����Cron���ʽ�Ƿ���ȷ,�׳��쳣˵��������
 * ��ȷ��ʾ����������10��ʱ��
 * 
 * @User yaowenjie
 * @Date 2016-07-20
 * @Time ����10:21:04
 */
@Component
public class CronExpressBuilder {
	
	public static void main(String[] args) {
		try {
			CronExpression exp = new CronExpression("0/10 * * * * ?");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			int i = 0;
			// ѭ���õ�������10�εĴ���ʱ��㣬����֤
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
