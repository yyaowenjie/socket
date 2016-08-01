package com.sq.socket.server;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.sq.drogon.component.BaseConfiguration;

/**
 * SocketServer配置基类
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time 下午2:22:53
 */
@Component
public final class BaseSocketServerConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BaseSocketServerConfiguration.class);

	private static Properties prop;

	public final static String CONFIG_PORT = "PORT";
	
	/** 属性文件路径 */
	private final static String CONFIG_FILE_NAME = "/conf/socket-server-config.properties";

	/**
	 * 加载配置文件
	 */
	static {
		loadPorpertityInfomation();
		fillSocketConnInformation();
	}

	/**
	 * 加载socket属性文件
	 * 
	 * @return prop
	 */
	private static Properties loadPorpertityInfomation() {
		prop = new Properties();
		try {
			prop.load(BaseConfiguration.class
					.getResourceAsStream(CONFIG_FILE_NAME));
		} catch (IOException e) {
			log.error("加载属性文件失败！");
		}
		return prop;
	}

	/**
	 * 组装socket属性信息到对象中
	 */
	private static void fillSocketConnInformation() {
		SocketServerInfoDomain socketServerInfoDomain = SocketServerInfoDomain
				.getInstance();
		socketServerInfoDomain.set_socket_port(Integer.parseInt(getEntryValue(CONFIG_PORT)));		
	}

	/**
	 * 通过名字获得配置的值
	 * 
	 * @param name
	 * @return
	 */
	public static String getEntryValue(String name) {
		return prop.getProperty(name);
	}

}
