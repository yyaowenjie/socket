package com.sq.yaml.domain;

/**
 * 请求响应头
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time 上午11:49:31
 */
public class HeadRequest {
	
	/** 创建时间 */
	private String createTime;
	
	/** 创建来源 */
	private String createUser;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "HeadRequest [createTime=" + createTime + ", createUser="
				+ createUser + "]";
	}
	
}
