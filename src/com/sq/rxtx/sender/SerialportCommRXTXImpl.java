package com.sq.rxtx.sender;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 串行端口推送器实现类
 * 
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time 下午4:50:57
 */
@Service
public class SerialportCommRXTXImpl implements SerialportCommBase<CommPortIdentifier> {

	private static final Logger log = LoggerFactory.getLogger(SerialportCommRXTXImpl.class);

	@Autowired
	public RXTXSerialReader rxtxSerialReader;

	/**
	 * 获取正在使用中的串口
	 */
	
	public HashSet<CommPortIdentifier> getAvailableSerialPorts() {
		HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
		Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
		while (thePorts.hasMoreElements()) {
			CommPortIdentifier com = (CommPortIdentifier) thePorts
					.nextElement();
			switch (com.getPortType()) {
			// RS232
			case CommPortIdentifier.PORT_SERIAL:
				try {
					CommPort thePort = com.open("CommUtil", 50);
					thePort.close();
					h.add(com);
					log.error("可用的端口有:"+com.getName());
				} catch (PortInUseException e) {
					log.error("端口在使用中" + com.getName());
				} catch (Exception e) {
					log.error(e+com.getName());
				}
			}
		}
		return h;
	}

	/**
	 * 列表所有的可用串口 参看父类中的注释 @see
	 * com.sn.communication.manager.serialport.SerialportCommBase#listAllPorts()
	 */

	public HashSet<CommPortIdentifier> listAllPorts() {
		HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = (CommPortIdentifier) portEnum
					.nextElement();
			h.add(portIdentifier);
		}
		return h;
	}

	/**
	 * 向串口中发送数据 参看父类中的注释 @see
	 * com.sn.communication.manager.serialport.SerialportCommBase
	 * #sendMessage(java.lang.String)
	 */

	public void sendMessage(String message) {
		this.rxtxSerialReader.run(message);
	}

	/**
	 * 开启串口端口 参看父类中的注释 @see
	 * com.sn.communication.manager.serialport.SerialportCommBase
	 * #openSerialPort(java.lang.String)
	 */

	public void openSerialPort(String port) {
		HashMap<String, Comparable> params = new HashMap<String, Comparable>();
		String dataBit = "" + SerialPort.DATABITS_8;
		String stopBit = "" + SerialPort.STOPBITS_1;
		int parityInt = SerialPort.PARITY_NONE;
		params.put(SerialportCommConsts.PARAMS_PORT, port); // 端口名称
		params.put(SerialportCommConsts.PARAMS_RATE,
				SerialportCommConsts.SERIALPORT_RATE); // 波特率
		params.put(SerialportCommConsts.PARAMS_DATABITS, dataBit); // 数据位
		params.put(SerialportCommConsts.PARAMS_STOPBITS, stopBit); // 停止位
		params.put(SerialportCommConsts.PARAMS_PARITY, parityInt); // 无奇偶校验
		params.put(SerialportCommConsts.PARAMS_TIMEOUT, 100); // 设备超时时间 1秒
		params.put(SerialportCommConsts.PARAMS_DELAY, 100); // 端口数据准备时间 1秒
		try {
			this.rxtxSerialReader.open(params);// 打开串口
			this.rxtxSerialReader.start();
		} catch (Exception e) {
			log.error("开启端口异常" + e);
		}
	}

	/**
	 * 关闭串口 参看父类中的注释 @see
	 * com.sn.communication.manager.serialport.SerialportCommBase
	 * #closeSerialPort(java.lang.String)
	 */

	public void closeSerialPort(String port) {
		this.rxtxSerialReader.close();
	}


}
