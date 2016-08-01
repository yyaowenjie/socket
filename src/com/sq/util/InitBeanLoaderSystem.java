package com.sq.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import com.sq.socket.server.WInitServerSocketSys;
/**
 * Bean����ѡ���Բ�������
 * 
 * @User:yaowenjie
 * @Date 2016-06-22
 * @Time 13:34
 */
@Component
public class InitBeanLoaderSystem implements BeanPostProcessor{

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
	/**
	 * Spring bean ���غ�ִ���Զ��巽��
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		/** ����socket���� */
		if(bean instanceof WInitServerSocketSys){
			((WInitServerSocketSys)bean).init();
		}
		return bean;
	}

}
