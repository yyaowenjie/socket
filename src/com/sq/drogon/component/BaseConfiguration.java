package com.sq.drogon.component;

import java.io.IOException;
import java.util.Properties;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.sq.drogon.domain.OpcServerEntrepot;

/**
 * 配置文件工具类 读取属性文件里的server信息
 * 
 * @User yaowenjie
 * @Date 2016-06-14
 * @Time 15:40
 */
@Component
public final class BaseConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BaseConfiguration.class);

	private static Properties prop;

	public final static String CONFIG_USERNAME = "username";
	public final static String CONFIG_PASSWORD = "password";
	public final static String CONFIG_HOST = "host";
	public final static String CONFIG_DOMAIN = "domain";
	public final static String CONFIG_CLSID = "clsid";
	public final static String CONFIG_PROGID = "progid";
	public final static String CONFIG_SYSID = "sysid";
	private final static String CONFIG_FILE_NAME = "/conf/opc-config.properties";

	/** 获取属性文件支持的最大的客户端的数量 */
	public static int CONFIG_MAX_CLIENT;

	/** 属性文件最大客户端数量关键字 */
	public static String CONFIG_CLIENT_PROP = "client_number";

	/** 初始客户端的数量 */
	public static int CONFIG_INIT_CLIENT = 1;

	/**
	 * 加载配置文件
	 */
	static {
		loadPorpertityInfomation();
		fillOpcConnInformation();
	}

	/**
	 * 加载opc属性文件
	 * 
	 * @return prop
	 */
	private static Properties loadPorpertityInfomation() {
		prop = new Properties();
		try {
			prop.load(BaseConfiguration.class
					.getResourceAsStream(CONFIG_FILE_NAME));
			CONFIG_MAX_CLIENT = Integer.parseInt(prop
					.getProperty(CONFIG_CLIENT_PROP));
		} catch (IOException e) {
			log.error("加载属性文件失败！");
		}
		return prop;
	}
	
	/**
	 * 循环拼接opc连接信息
	 * create by yaowenjie
	 */
	private static void fillOpcConnInformation() {
		while (CONFIG_INIT_CLIENT <= CONFIG_MAX_CLIENT) {
			givingConncetion(CONFIG_INIT_CLIENT);
			CONFIG_INIT_CLIENT++;
		}
	}

	/**
	 * 拼装连接信息
	 * 
	 * @param sysId
	 */
	public static void givingConncetion(Integer sysId) {
		OpcServerEntrepot opcServerEntrepot = new OpcServerEntrepot();
		ConnectionInformation connectionInformation = new ConnectionInformation();
		String connector = "->" + sysId;
		connectionInformation.setUser(getEntryValue(CONFIG_USERNAME + connector));
		connectionInformation.setClsid(getEntryValue(CONFIG_CLSID + connector));
		connectionInformation.setPassword(getEntryValue(CONFIG_PASSWORD + connector));
		connectionInformation.setDomain(getEntryValue(CONFIG_DOMAIN + connector));
		connectionInformation.setHost(getEntryValue(CONFIG_HOST + connector));
		connectionInformation.setProgId(getEntryValue(CONFIG_PROGID + connector));
		opcServerEntrepot.setConnectionInformation(connectionInformation);
		CacheOpcEntrepoty.putServerInfo(sysId,opcServerEntrepot);
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
	
	  /**
     * 获得包含ClsId的连接信息
     *
     * @return
     */
    public static ConnectionInformation getCLSIDConnectionInfomation(Integer sysId) {
        ConnectionInformation ci = CacheOpcEntrepoty.getConnctInfo(sysId);
        ci.setProgId(null);
        ci.setClsid(ci.getClsid());
        return ci;
    }

}
