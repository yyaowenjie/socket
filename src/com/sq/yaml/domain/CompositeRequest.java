package com.sq.yaml.domain;

import java.io.Serializable;
import java.util.List;

/**
 * �ۺ���������
 * 
 * @param <T>
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time ����11:49:02
 */
public class CompositeRequest<T> implements Serializable{
	
	/**   ���л�ID    */
	private static final long serialVersionUID = 1L;
	
	/**  ����ͷ */
	private HeadRequest head;
	
	/**  �������ݼ���  */
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
