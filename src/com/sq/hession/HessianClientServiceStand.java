package com.sq.hession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Hessian客户端实现类，其实没多大卵用
 * @User yaowenjie
 * @Date 2016-7-5
 * @Time 下午2:53:48
 */
@Service
public class HessianClientServiceStand  {
	
	private static final Logger log = LoggerFactory.getLogger(HessianClientServiceStand.class);
	
	public String sendMessageToHessian(String password) {
		log.error("客户端发送的数据是！"+ password);
		return password;
	}


	

}
