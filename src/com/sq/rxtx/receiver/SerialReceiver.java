package com.sq.rxtx.receiver;

/**
 * 串口接收端口开启method
 * 
 * @User yaowenjie
 * @Date 2016-7-28
 * @Time 下午12:23:18
 */
public class SerialReceiver {
	
	public static void main(String[] args) {
		SerialReader serialReader = new SerialReader();
		serialReader.openSerialPort("串行接口开启...");
	}
}
