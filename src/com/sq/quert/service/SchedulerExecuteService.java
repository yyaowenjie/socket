package com.sq.quert.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring定时逻辑业务类
 * 
 * @User yaowenjie
 * @Date 2016-06-15
 * @Time 09:10
 */
public class SchedulerExecuteService {
	
	private static final Logger log = LoggerFactory.getLogger(SchedulerExecuteService.class);

	public void startQuertzCollection() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		log.error(df.format(c.getTime())+"*************start*************");
	}
	
	public void middlwQuertzCollection() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		log.error(df.format(c.getTime())+"$$$$$$$$$$$$$middle$$$$$$$$$$$$");
	}
	
	public void endQuertzCollection() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		log.error(df.format(c.getTime())+"…………………end…………………");
	}
}
