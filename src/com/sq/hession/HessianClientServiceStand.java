package com.sq.hession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Hessian�ͻ���ʵ���࣬��ʵû�������
 * @User yaowenjie
 * @Date 2016-7-5
 * @Time ����2:53:48
 */
@Service
public class HessianClientServiceStand  {
	
	private static final Logger log = LoggerFactory.getLogger(HessianClientServiceStand.class);
	
	public String sendMessageToHessian(String password) {
		log.error("�ͻ��˷��͵������ǣ�"+ password);
		return password;
	}


	

}
