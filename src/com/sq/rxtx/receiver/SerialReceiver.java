package com.sq.rxtx.receiver;

/**
 * ���ڽ��ն˿ڿ���method
 * 
 * @User yaowenjie
 * @Date 2016-7-28
 * @Time ����12:23:18
 */
public class SerialReceiver {
	
	public static void main(String[] args) {
		SerialReader serialReader = new SerialReader();
		serialReader.openSerialPort("���нӿڿ���...");
	}
}
