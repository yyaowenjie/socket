package com.sq.socket.server;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.sq.drogon.component.BaseConfiguration;

/**
 * SocketServer���û���
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time ����2:22:53
 */
@Component
public final class BaseSocketServerConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BaseSocketServerConfiguration.class);

	private static Properties prop;

	public final static String CONFIG_PORT = "PORT";
	
	/** �����ļ�·�� */
	private final static String CONFIG_FILE_NAME = "/conf/socket-server-config.properties";

	/**
	 * ���������ļ�
	 */
	static {
		loadPorpertityInfomation();
		fillSocketConnInformation();
	}

	/**
	 * ����socket�����ļ�
	 * 
	 * @return prop
	 */
	private static Properties loadPorpertityInfomation() {
		prop = new Properties();
		try {
			prop.load(BaseConfiguration.class
					.getResourceAsStream(CONFIG_FILE_NAME));
		} catch (IOException e) {
			log.error("���������ļ�ʧ�ܣ�");
		}
		return prop;
	}

	/**
	 * ��װsocket������Ϣ��������
	 */
	private static void fillSocketConnInformation() {
		SocketServerInfoDomain socketServerInfoDomain = SocketServerInfoDomain
				.getInstance();
		socketServerInfoDomain.set_socket_port(Integer.parseInt(getEntryValue(CONFIG_PORT)));		
	}

	/**
	 * ͨ�����ֻ�����õ�ֵ
	 * 
	 * @param name
	 * @return
	 */
	public static String getEntryValue(String name) {
		return prop.getProperty(name);
	}

}
