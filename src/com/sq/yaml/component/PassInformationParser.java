package com.sq.yaml.component;

import java.util.List;
import com.sq.yaml.domain.CompositeRequest;
import com.sq.yaml.domain.HeadRequest;
import com.sq.yaml.domain.IncludeRequest;

/**
 * ��װ������Ϣ������
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time ����1:06:04
 */
public class PassInformationParser {
	
	private static PassInformationParser passInformationParser;
	
	/**
	 * ʵ����������Ϣ
	 * @return
	 */
	public static PassInformationParser createParserInstance(){
		if(passInformationParser == null){
			passInformationParser = new PassInformationParser();
		}
		return passInformationParser;
	}
	/**
	 * ��װ�����Ϣ
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
	 * ��װ����ͷ��Ϣ
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
