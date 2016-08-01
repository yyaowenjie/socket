package com.sq.rxtx.sender;

import gnu.io.CommPortIdentifier;

import java.util.HashSet;

/**
 * ����ͨѶ��׼���ӿ�
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time ����4:53:45
 * @param <T>
 */
public interface SerialportCommBase<T> {

	/**
	 * ��ȡ���е�ʹ���еĴ���
	 * @return ���ؾ���ʵ�ָ÷���Ĵ������Ӷ���
	 */
	public HashSet<T> getAvailableSerialPorts();
	
	/**
	 * �б����еĿ��ô���
	 * @return ���ؾ���ʵ�ָ÷���Ĵ���
	 */
	public HashSet<CommPortIdentifier> listAllPorts();
	
	/**
	 * ��ָ���Ĵ��ڷ���������
	 * @param port    ָ���˿�
	 * @param message ��Ҫ�������Ϣ�ı�
	 */
	public void sendMessage(String message);
	
	/**
	 * ����ָ���Ķ˿ڣ������������
	 * @param portName ָ���Ķ˿�
	 */
	public void openSerialPort(String portName);
	
	/**
	 * �Ͽ�ָ������֮�������
	 * @param portName ��������
	 */
	public void closeSerialPort(String portName);
}
