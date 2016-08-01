package com.sq.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.stereotype.Service;

import com.sn.core.exception.InvokeWebServiceException;

/**
 * Web Service客户端控制接口
 * @User yaowenjie
 * @Date 2016-6-27
 * @Time 下午2:35:04
 */
@Service
public class WsClientUtilService {
	
	/**
	 * 使用拼装好的接口报文，调用指定WSDL路径的指定方法。 客户端使用CXF使用
	 * @param xmlRequest 接口报文字符串。
	 * @param wsdlUrl 服务器端WSDL路径
	 * @param methodName 服务器端方法名
	 * @return 响应报文字符串。
	 */
	public String callCxfDynamic (String xmlRequest, String wsdlUrl, String methodName) {
		String resObj = "";
		try {
			if(StringUtils.isBlank(wsdlUrl)){
				throw new InvokeWebServiceException("请提供访问WebService接口方法的URL");
			}
			if(StringUtils.isBlank(methodName)){
				throw new InvokeWebServiceException("请提供要调用的WebService接口方法!");
			}
			JaxWsDynamicClientFactory factory =JaxWsDynamicClientFactory.newInstance();
			Client client =factory.createClient(wsdlUrl);
			
			//设置超时单位为毫秒  
	        HTTPConduit http = (HTTPConduit) client.getConduit();        
	        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();        
	        httpClientPolicy.setConnectionTimeout(3000000);  //连接超时      
	        httpClientPolicy.setAllowChunking(false);    //取消块编码   
	        httpClientPolicy.setReceiveTimeout(3000000);     //响应超时  
	        http.setClient(httpClientPolicy);  
			
	    	Object[] obj =client.invoke(methodName,xmlRequest);
	    	resObj = obj[0].toString();
		}  catch (Exception e) {
		    throw new InvokeWebServiceException("调用Webservice接口方法出现未知异常!",e);
		}
		return resObj;
	}
}
