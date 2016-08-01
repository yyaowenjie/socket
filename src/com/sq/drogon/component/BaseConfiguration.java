package com.sq.drogon.component;

import java.io.IOException;
import java.util.Properties;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.sq.drogon.domain.OpcServerEntrepot;

/**
 * �����ļ������� ��ȡ�����ļ����server��Ϣ
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

	/** ��ȡ�����ļ�֧�ֵ����Ŀͻ��˵����� */
	public static int CONFIG_MAX_CLIENT;

	/** �����ļ����ͻ��������ؼ��� */
	public static String CONFIG_CLIENT_PROP = "client_number";

	/** ��ʼ�ͻ��˵����� */
	public static int CONFIG_INIT_CLIENT = 1;

	/**
	 * ���������ļ�
	 */
	static {
		loadPorpertityInfomation();
		fillOpcConnInformation();
	}

	/**
	 * ����opc�����ļ�
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
			log.error("���������ļ�ʧ�ܣ�");
		}
		return prop;
	}
	
	/**
	 * ѭ��ƴ��opc������Ϣ
	 * create by yaowenjie
	 */
	private static void fillOpcConnInformation() {
		while (CONFIG_INIT_CLIENT <= CONFIG_MAX_CLIENT) {
			givingConncetion(CONFIG_INIT_CLIENT);
			CONFIG_INIT_CLIENT++;
		}
	}

	/**
	 * ƴװ������Ϣ
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
	 * ͨ�����ֻ�����õ�ֵ
	 * 
	 * @param name
	 * @return
	 */
	public static String getEntryValue(String name) {
		return prop.getProperty(name);
	}
	
	  /**
     * ��ð���ClsId��������Ϣ
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
