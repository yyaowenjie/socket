package com.sq.drogon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sq.hession.IHessianClientServiceStand;

/**
 * http测试Hessian传输报文
 * @User yaowenjie
 * @Date 2016-7-5
 * @Time 下午3:32:19
 */
@Controller
public class HessianController {
	
	private static final Logger log = LoggerFactory.getLogger(HessianController.class);
	 
	@ResponseBody
	@RequestMapping("dis/testFor.do")
	public String testFor(@RequestParam Integer sys) {
		String s = null;
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("springContext-hessian.xml");
			IHessianClientServiceStand hello = (IHessianClientServiceStand) context.getBean("hessianClient");
			s = hello.sendMessageToHessian("yaowenjie");
		} catch (Exception e) {
		    s = "Connection time out! please try!";
		}
		log.error("服务器端传回的信息是：" + s);
		return s;
	}
}
