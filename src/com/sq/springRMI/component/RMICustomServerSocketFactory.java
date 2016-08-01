package com.sq.springRMI.component;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;
import org.springframework.stereotype.Component;
/**
 * RMICustomServerSocketFactory,用于设置Server端
 * 限定时间没有client连接后抛出异常
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午11:39:48
 */

public class RMICustomServerSocketFactory implements RMIServerSocketFactory,Serializable {

	private static final long serialVersionUID = 8357061901854965297L;
	private int timeout;

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public ServerSocket createServerSocket(int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(timeout);
		return serverSocket;
	}
}
