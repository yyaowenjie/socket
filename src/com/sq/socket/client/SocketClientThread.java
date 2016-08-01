package com.sq.socket.client;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
/**
 * SocketClient�����߳�
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time ����3:11:40
 */
public class SocketClientThread implements Callable<Boolean>{
	
	private int port;
	
	private String host;
	
	/** �׽��� */
	private Socket socket;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public SocketClientThread(int port, String host) {
		this.port = port;
		this.host = host;
	}
	
	/**
	 * ����success��Server�ˣ���ɺ󷵻�true
	 */
	public Boolean call() throws Exception {
		socket = new Socket(host, port);
		BufferedWriter rdr = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		rdr.write("success");
		rdr.close();
		return true;
	}
	
	
}
