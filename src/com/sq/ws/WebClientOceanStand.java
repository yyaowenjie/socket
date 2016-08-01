package com.sq.ws;

import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.sq.util.WsClientUtilService;

@Service
@Qualifier("iWebClientOceanStand")
@WebService(endpointInterface = "com.sq.ws.IWebClientOceanStand", serviceName = "IWebClientOceanStand")
public class WebClientOceanStand implements IWebClientOceanStand{
	
	private static final Logger log = LoggerFactory.getLogger(WebClientOceanStand.class);

	@Autowired
	private WsClientUtilService wsClientUtilService;
	
	public boolean testSendWS(String str) {
		log.error("�ͻ��˿�ʼ������Ϣ:" + str);
		String wsdlUrl = "http://localhost:8888/common/ws/IWebServiceOceanStand?wsdl";
		String request = wsClientUtilService.callCxfDynamic("yaowenjie��", wsdlUrl, "testReceiveWS");
		log.error("�������˴��ص���Ϣ��:" + request);
		return true;
	}

}
