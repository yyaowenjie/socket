package com.sq.yaml.domain;

/**
 * ������Ӧͷ
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time ����11:49:31
 */
public class HeadRequest {
	
	/** ����ʱ�� */
	private String createTime;
	
	/** ������Դ */
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
