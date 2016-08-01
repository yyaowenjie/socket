package com.sq.yaml.domain;


/**
 * 显示报文类型是Request
 * 
 * @User yaowenjie
 * @Date 2016-7-11
 * @Time 下午4:17:14
 * @param <T>
 */
public class IncludeRequest<T> {
	@SuppressWarnings("rawtypes")
	private static IncludeRequest includeRequest;
	
	/**
	 * 实例化对象信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static IncludeRequest createIncludeRequest(){
		if(includeRequest == null){
			includeRequest = new IncludeRequest();
		}
		return includeRequest;
	}
	
	private CompositeRequest<T> request;
	
	
	public CompositeRequest<T> getRequest() {
		return request;
	}

	public void setRequest(CompositeRequest<T> request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "IncludeRequest [request=" + request + "]";
	}

	
}
