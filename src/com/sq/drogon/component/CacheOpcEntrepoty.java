package com.sq.drogon.component;

import java.net.UnknownHostException;
import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.sq.drogon.domain.OpcServerEntrepot;

/**
 * 缓存连接信息工具
 * @User yaowenjie
 * @Date 2016-06-16
 * @Time 10:32
 */
@Component
public final class CacheOpcEntrepoty {
	
	private static final Logger log = LoggerFactory.getLogger(CacheOpcEntrepoty.class);

	/** opc连接配置信息 K：sysId V：配置信息DTO */
	public static AbstractMap<Integer, OpcServerEntrepot> conInfoMap = new ConcurrentHashMap<Integer, OpcServerEntrepot>();
	
	/**
	 * 缓存连接信息DTO
	 * @param sysId
	 * @param serverInfomation
	 */
    public static void putServerInfo (int sysId, OpcServerEntrepot serverEntrepot) {
        conInfoMap.put(sysId,serverEntrepot);
    } 
    	
    /**
     * 获取缓存连接信息DTO
     * @param sysId
     */
    public static OpcServerEntrepot getServerInfo (Integer sysId) {
        return conInfoMap.get(sysId);
    } 
    
    /**
     * 获取缓存连接信息
     * @param sysId
     */
    public static ConnectionInformation getConnctInfo (Integer sysId) {
       return conInfoMap.get(sysId).getConnectionInformation();
    } 
    /**
     * 连接目标host的opc server
     * @return 服务连接
     */
    public static Server connect(int sid) {

        Server server = new Server(
                BaseConfiguration.getCLSIDConnectionInfomation(sid),
                Executors.newSingleThreadScheduledExecutor());
        server.setDefaultLocaleID(sid);
        try {
            server.connect();
        } catch (UnknownHostException e) {
            log.error("目标host的地址错误", e);
        } catch (JIException e) {
            log.error("获取配置文件中内容时出错",e);
        } catch (AlreadyConnectedException e) {
            log.error("连接已经存在，无需再次连接",e);
        }
        return server;
    }

    /**
     * 断开与目标host地址上opc server的连接
     */
    public void disServer(Server server) {
        server.dispose();
    }
    
}
