package com.sq.drogon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sq.ws.WebClientOceanStand;
/**
 * 手动触发web service
 * @User yaowenjie
 * @Date 2016-6-27
 * @Time 下午2:21:48
 */
@Controller
public class HandOperWebClientOceanStand {
	
	private static final Logger log = LoggerFactory.getLogger(HandOperWebClientOceanStand.class);
	 
	@Autowired
	private WebClientOceanStand webClientOceanStand;
	
	@ResponseBody
	@RequestMapping("webService/clientStartSendMsg.do")
	public String clientStartSendMsg(@RequestParam String str){
		boolean flag = webClientOceanStand.testSendWS(str);
		return flag+"";
	}
}
