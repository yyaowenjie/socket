package com.sq.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import com.sq.socket.server.WInitServerSocketSys;
/**
 * Bean加载选择性操作方法
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
	 * Spring bean 加载后执行自定义方法
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		/** 开启socket服务 */
		if(bean instanceof WInitServerSocketSys){
			((WInitServerSocketSys)bean).init();
		}
		return bean;
	}

}
