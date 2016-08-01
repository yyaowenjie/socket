package com.sq.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Socket�����߳�
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time ����3:24:21
 */
public class SocketAcceptThread extends Thread{
	
	private static final Logger log = LoggerFactory.getLogger(SocketAcceptThread.class);
	
	private ServerSocket socketServer;
	
	private Socket socket;

	public SocketAcceptThread(ServerSocket socketServer) {
		this.socketServer = socketServer;
	}
	@Override
	public void run() {
		try {
			while (true) {
				socket = socketServer.accept();
				PrintWriter wtr = new PrintWriter(socket.getOutputStream());
				BufferedReader rdr = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String line = null;
				line = rdr.readLine();
				//�˴����Բ�����������
				log.error("*************OceanServer���յ�����ϢΪ��" + line);
				wtr.flush();			
			}
		} catch (IOException e) {
			log.error("Server�����̴߳���.....");
		}
	}
	
}
