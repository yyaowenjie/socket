package com.sq.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SocketServer启动线程
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time 下午3:15:51
 */
public class SocketServerThread extends Thread{
	
	private static final Logger log = LoggerFactory.getLogger(SocketServerThread.class);
	
	private int port;
	
	private ServerSocket socketServer;	
	
	public SocketServerThread(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			socketServer = new ServerSocket(port);
			SocketAcceptThread socketAcceptThread = new SocketAcceptThread(socketServer);
			socketAcceptThread.start();
		} catch (IOException e) {
			log.error("初始化SocketServer错误！");
		}
	}
}
