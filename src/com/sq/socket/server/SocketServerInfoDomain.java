package com.sq.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SocketServer信息包装类
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time 下午1:55:25
 */
public class SocketServerInfoDomain {
	
	private static final Logger log = LoggerFactory.getLogger(SocketServerInfoDomain.class);
	
	private Integer _socket_port ;
	
	private static SocketServerInfoDomain socketServerInfoDomain;
		
	/** 设置成私有属性,构造单例模式 */
	private SocketServerInfoDomain(){}
	
	public Integer get_socket_port() {
		return _socket_port;
	}

	public void set_socket_port(Integer _socket_port) {
		this._socket_port = _socket_port;
	}
	/**
	 * 构造SocketServer单例
	 * 
	 * @return
	 */
	public static synchronized SocketServerInfoDomain getInstance() {
		if (socketServerInfoDomain == null)
			socketServerInfoDomain = new SocketServerInfoDomain();
		log.info("构造一次SocketServerInfo对象!!");
		return socketServerInfoDomain;
	}
	
	
}
