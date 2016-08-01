package com.sq.yaml.component;

import java.util.List;
import com.sq.yaml.domain.CompositeRequest;
import com.sq.yaml.domain.HeadRequest;
import com.sq.yaml.domain.IncludeRequest;

/**
 * 组装传递信息发动机
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time 下午1:06:04
 */
public class PassInformationParser {
	
	private static PassInformationParser passInformationParser;
	
	/**
	 * 实例化对象信息
	 * @return
	 */
	public static PassInformationParser createParserInstance(){
		if(passInformationParser == null){
			passInformationParser = new PassInformationParser();
		}
		return passInformationParser;
	}
	/**
	 * 组装组合信息
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings(value={"unchecked", "rawtypes"})
	public <T> IncludeRequest parserRequestMessage(String createTime,List<T> tooMay){
		HeadRequest headRequest = PassInformationParser.this.parserHeadMessage(createTime);
		IncludeRequest<CompositeRequest> includeRequest = IncludeRequest.createIncludeRequest();
		CompositeRequest compositeRequest = new CompositeRequest();
		compositeRequest.setHead(headRequest);
		compositeRequest.setRequestData(tooMay);
		includeRequest.setRequest(compositeRequest);
		return includeRequest;
	}
	
	/**
	 * 组装请求头信息
	 * @param createTime
	 * @return
	 */
	public HeadRequest parserHeadMessage(String createTime){
		HeadRequest headRequest = new HeadRequest();
		headRequest.setCreateTime(createTime);
		headRequest.setCreateUser("ocean");
		return headRequest;
	}
	

}
