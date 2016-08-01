package com.sq.yaml.iml;

import java.util.ArrayList;
import java.util.List;
import com.sq.yaml.component.PassInformationParser;
import com.sq.yaml.component.YamlComponent;
import com.sq.yaml.domain.CompositeRequest;
import com.sq.yaml.domain.DataTempRequest;
import com.sq.yaml.domain.IncludeRequest;

/**
 * Yaml 适配器
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time 上午11:37:32
 */
public class YamlParseInstanceCurrent {
	
	/**
	 * 主方法
	 * @return
	 */
	public static synchronized boolean passMessageCurrent(String createTime,List<String> list){
		boolean isSuccess = false;
		PassInformationParser passInformationParser = PassInformationParser.createParserInstance();
		List<DataTempRequest> requestList = new ArrayList<DataTempRequest>();
		for(String s : list){
			DataTempRequest dataTempRequest = new DataTempRequest();
			dataTempRequest.setDataCode(s);
			requestList.add(dataTempRequest);
		}
		IncludeRequest<CompositeRequest<DataTempRequest>> includeRequest = passInformationParser.parserRequestMessage(createTime, requestList);
		System.out.println(YamlComponent.DomainSerializationYaml(includeRequest));
		return isSuccess;
	}
	/*******************TestT******************/
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("CER");
		list.add("RFK");
		list.add("HOG");
		passMessageCurrent("20160708",list);
	}
	/******************************************/
}
