package com.sq.socket.client;

import java.util.concurrent.FutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SocketClient������
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time ����2:14:59
 */
public class ControlSocketSendMessage {

	private static final Logger log = LoggerFactory.getLogger(ControlSocketSendMessage.class);

	private static SocketClientInfoDomain socketClientInfoDomain;
	
	public static synchronized SocketClientInfoDomain getInstance() {
		if (socketClientInfoDomain == null)
			socketClientInfoDomain = new SocketClientInfoDomain();
		log.info("����һ��SocketInfo����!��");
		return socketClientInfoDomain;
	}

	/**
	 * ����Socket�߳�
	 */
	public void startSendMessageToServer() {
		SocketClientThread task = new SocketClientThread(getInstance().get_socket_port(), getInstance().get_server_host());
		FutureTask<Boolean> futureTask = new FutureTask<Boolean>(task);
		Thread thread = new Thread(futureTask);
		thread.start();
		try {
			while (!futureTask.isDone()) {
				Boolean flag = futureTask.get();
				log.error("*************Ocean������Ϣ����" + flag);
			}
		} catch (Exception e) {
			log.error("�߳��쳣��������");
		}

	}
}
