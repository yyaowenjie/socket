package com.sq.rxtx.receiver;

import gnu.io.SerialPort;

/**
 * ���нӿڽ��ճ���
 * 
 * @User yaowenjie
 * @Date 2016-7-28
 * @Time ����11:49:51
 */
public interface SerialReaderConsts {
	
	/** �������ڶ˿ں� */
	public static final String port = "COM2";
	/** �������ڶ˿�Ƶ�� */
	public static final String rate = "57600";
	/** ����λ */
	public static final String dataBit = "" + SerialPort.DATABITS_8;
	/** ֹͣλ */
	public static final String stopBit = "" + SerialPort.STOPBITS_1;
	/** ��żУ�� */
	public static final String parity = "" + SerialPort.PARITY_NONE;
	
	int parityInt = SerialPort.PARITY_NONE;
	/** �˿ڶ��������¼�������,�ȴ�n������ٶ�ȡ,�Ա�������һ���Զ��� */
	/** ��ʱ�ȴ��˿�����׼����ʱ�� */
	public static final String PARAMS_DELAY = "delay read";
	/** ��ʱʱ�� */
	public static final String PARAMS_TIMEOUT = "timeout";
	/** �˿����� */
	public static final String PARAMS_PORT = "port name"; //
	/** ����λ */
	public static final String PARAMS_DATABITS = "data bits";
	/** ֹͣλ */
	public static final String PARAMS_STOPBITS = "stop bits"; //
	/** ��żУ�� */
	public static final String PARAMS_PARITY = "parity";
	/** ������ */
	public static final String PARAMS_RATE = "rate";
}
