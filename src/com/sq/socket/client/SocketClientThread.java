package com.sq.socket.client;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
/**
 * SocketClient推送线程
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time 下午3:11:40
 */
public class SocketClientThread implements Callable<Boolean>{
	
	private int port;
	
	private String host;
	
	/** 套接字 */
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
	 * 推送success到Server端，完成后返回true
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
