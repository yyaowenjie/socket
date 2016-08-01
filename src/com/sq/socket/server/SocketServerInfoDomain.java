package com.sq.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SocketServer��Ϣ��װ��
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time ����1:55:25
 */
public class SocketServerInfoDomain {
	
	private static final Logger log = LoggerFactory.getLogger(SocketServerInfoDomain.class);
	
	private Integer _socket_port ;
	
	private static SocketServerInfoDomain socketServerInfoDomain;
		
	/** ���ó�˽������,���쵥��ģʽ */
	private SocketServerInfoDomain(){}
	
	public Integer get_socket_port() {
		return _socket_port;
	}

	public void set_socket_port(Integer _socket_port) {
		this._socket_port = _socket_port;
	}
	/**
	 * ����SocketServer����
	 * 
	 * @return
	 */
	public static synchronized SocketServerInfoDomain getInstance() {
		if (socketServerInfoDomain == null)
			socketServerInfoDomain = new SocketServerInfoDomain();
		log.info("����һ��SocketServerInfo����!!");
		return socketServerInfoDomain;
	}
	
	
}
