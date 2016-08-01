package com.sq.springRMI.component;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import org.springframework.stereotype.Component;

/**
 * ���ó�ʱʱ��,(ֻ�������ڷ�������)
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time ����11:36:41
 */

public class RMICustomClientSocketFactory implements RMIClientSocketFactory,Serializable {

	private static final long serialVersionUID = 2879181835011310833L;
	private int timeout;

	/**
	 * ���ó�ʱʱ��
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Socket createSocket(String host, int port) throws IOException {
		Socket socket = new Socket();
		socket.setSoTimeout(timeout);
		socket.setSoLinger(false, 0);
		socket.setKeepAlive(false);
		socket.connect(new InetSocketAddress(host, port), timeout);
		return socket;
	}
}