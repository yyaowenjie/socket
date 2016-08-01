package com.sq.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.stereotype.Service;

import com.sn.core.exception.InvokeWebServiceException;

/**
 * Web Service�ͻ��˿��ƽӿ�
 * @User yaowenjie
 * @Date 2016-6-27
 * @Time ����2:35:04
 */
@Service
public class WsClientUtilService {
	
	/**
	 * ʹ��ƴװ�õĽӿڱ��ģ�����ָ��WSDL·����ָ�������� �ͻ���ʹ��CXFʹ��
	 * @param xmlRequest �ӿڱ����ַ�����
	 * @param wsdlUrl ��������WSDL·��
	 * @param methodName �������˷�����
	 * @return ��Ӧ�����ַ�����
	 */
	public String callCxfDynamic (String xmlRequest, String wsdlUrl, String methodName) {
		String resObj = "";
		try {
			if(StringUtils.isBlank(wsdlUrl)){
				throw new InvokeWebServiceException("���ṩ����WebService�ӿڷ�����URL");
			}
			if(StringUtils.isBlank(methodName)){
				throw new InvokeWebServiceException("���ṩҪ���õ�WebService�ӿڷ���!");
			}
			JaxWsDynamicClientFactory factory =JaxWsDynamicClientFactory.newInstance();
			Client client =factory.createClient(wsdlUrl);
			
			//���ó�ʱ��λΪ����  
	        HTTPConduit http = (HTTPConduit) client.getConduit();        
	        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();        
	        httpClientPolicy.setConnectionTimeout(3000000);  //���ӳ�ʱ      
	        httpClientPolicy.setAllowChunking(false);    //ȡ�������   
	        httpClientPolicy.setReceiveTimeout(3000000);     //��Ӧ��ʱ  
	        http.setClient(httpClientPolicy);  
			
	    	Object[] obj =client.invoke(methodName,xmlRequest);
	    	resObj = obj[0].toString();
		}  catch (Exception e) {
		    throw new InvokeWebServiceException("����Webservice�ӿڷ�������δ֪�쳣!",e);
		}
		return resObj;
	}
}
