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
 * ���ж˿�������ʵ����
 * 
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time ����4:50:57
 */
@Service
public class SerialportCommRXTXImpl implements SerialportCommBase<CommPortIdentifier> {

	private static final Logger log = LoggerFactory.getLogger(SerialportCommRXTXImpl.class);

	@Autowired
	public RXTXSerialReader rxtxSerialReader;

	/**
	 * ��ȡ����ʹ���еĴ���
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
					log.error("���õĶ˿���:"+com.getName());
				} catch (PortInUseException e) {
					log.error("�˿���ʹ����" + com.getName());
				} catch (Exception e) {
					log.error(e+com.getName());
				}
			}
		}
		return h;
	}

	/**
	 * �б����еĿ��ô��� �ο������е�ע�� @see
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
	 * �򴮿��з������� �ο������е�ע�� @see
	 * com.sn.communication.manager.serialport.SerialportCommBase
	 * #sendMessage(java.lang.String)
	 */

	public void sendMessage(String message) {
		this.rxtxSerialReader.run(message);
	}

	/**
	 * �������ڶ˿� �ο������е�ע�� @see
	 * com.sn.communication.manager.serialport.SerialportCommBase
	 * #openSerialPort(java.lang.String)
	 */

	public void openSerialPort(String port) {
		HashMap<String, Comparable> params = new HashMap<String, Comparable>();
		String dataBit = "" + SerialPort.DATABITS_8;
		String stopBit = "" + SerialPort.STOPBITS_1;
		int parityInt = SerialPort.PARITY_NONE;
		params.put(SerialportCommConsts.PARAMS_PORT, port); // �˿�����
		params.put(SerialportCommConsts.PARAMS_RATE,
				SerialportCommConsts.SERIALPORT_RATE); // ������
		params.put(SerialportCommConsts.PARAMS_DATABITS, dataBit); // ����λ
		params.put(SerialportCommConsts.PARAMS_STOPBITS, stopBit); // ֹͣλ
		params.put(SerialportCommConsts.PARAMS_PARITY, parityInt); // ����żУ��
		params.put(SerialportCommConsts.PARAMS_TIMEOUT, 100); // �豸��ʱʱ�� 1��
		params.put(SerialportCommConsts.PARAMS_DELAY, 100); // �˿�����׼��ʱ�� 1��
		try {
			this.rxtxSerialReader.open(params);// �򿪴���
			this.rxtxSerialReader.start();
		} catch (Exception e) {
			log.error("�����˿��쳣" + e);
		}
	}

	/**
	 * �رմ��� �ο������е�ע�� @see
	 * com.sn.communication.manager.serialport.SerialportCommBase
	 * #closeSerialPort(java.lang.String)
	 */

	public void closeSerialPort(String port) {
		this.rxtxSerialReader.close();
	}


}
