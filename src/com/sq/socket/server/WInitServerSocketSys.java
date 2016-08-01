package com.sq.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * �̳߳�ʼ�����
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time ����3:33:35
 */
@Component
public class WInitServerSocketSys {
	
	private static final Logger log = LoggerFactory.getLogger(WInitServerSocketSys.class);
	
	public void init(){
		int port = SocketServerInfoDomain.getInstance().get_socket_port();
		SocketServerThread s = new SocketServerThread(port);
		s.start();
		log.error("server�����߳̿�ʼ����....");
	}
}
