package com.sq.yaml.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 综合请求处理类
 * 
 * @param <T>
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time 上午11:49:02
 */
public class CompositeRequest<T> implements Serializable{
	
	/**   序列化ID    */
	private static final long serialVersionUID = 1L;
	
	/**  请求头 */
	private HeadRequest head;
	
	/**  请求数据集合  */
	private List<T> requestData;

	public HeadRequest getHead() {
		return head;
	}

	public void setHead(HeadRequest head) {
		this.head = head;
	}

	public List<T> getRequestData() {
		return requestData;
	}

	public void setRequestData(List<T> requestData) {
		this.requestData = requestData;
	}
	
	
}
