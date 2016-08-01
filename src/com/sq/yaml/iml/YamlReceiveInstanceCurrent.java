package com.sq.yaml.iml;

import com.sq.yaml.component.YamlComponent;
import com.sq.yaml.domain.CompositeRequest;
import com.sq.yaml.domain.IncludeRequest;

/**
 * 接收传递Yaml请求报文
 * 
 * @User yaowenjie
 * @Date 2016-7-11
 * @Time 下午4:22:49
 */
public class YamlReceiveInstanceCurrent {
	
	/**
	 * 接收报文方法
	 * @param string
	 */
	@SuppressWarnings("rawtypes")
	public void receiveInstanceCurrent(String string){
		IncludeRequest<CompositeRequest> include = new IncludeRequest<CompositeRequest>();
		include = YamlComponent.YamlSerializationDomain(string, include);
		System.out.println(include.getRequest().getHead().getCreateTime());
		System.out.println(include.getRequest().getHead().getCreateUser());
		System.out.println(include.getRequest().getRequestData().get(0));
		System.out.println(include.getRequest().getRequestData().get(1));
		System.out.println(include.getRequest().getRequestData().get(2));
	}
}
