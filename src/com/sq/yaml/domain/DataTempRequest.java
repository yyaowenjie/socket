package com.sq.yaml.domain;
/**
 * 请求数据实例
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time 上午11:26:20
 */
public class DataTempRequest {
	
	private String dataCode;	

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	@Override
	public String toString() {
		return "DataTempRequest [dataCode=" + dataCode + "]";
	}
	
}
