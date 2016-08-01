package com.sq.rxtx.sender;

import gnu.io.CommPortIdentifier;

import java.util.HashSet;

/**
 * 串口通讯标准化接口
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time 下午4:53:45
 * @param <T>
 */
public interface SerialportCommBase<T> {

	/**
	 * 获取所有的使用中的串口
	 * @return 返回具体实现该服务的串口连接对象
	 */
	public HashSet<T> getAvailableSerialPorts();
	
	/**
	 * 列表所有的可用串口
	 * @return 返回具体实现该服务的串口
	 */
	public HashSet<CommPortIdentifier> listAllPorts();
	
	/**
	 * 向指定的串口发送数据流
	 * @param port    指定端口
	 * @param message 需要传输的消息文本
	 */
	public void sendMessage(String message);
	
	/**
	 * 连接指定的端口，打开输入输出流
	 * @param portName 指定的端口
	 */
	public void openSerialPort(String portName);
	
	/**
	 * 断开指定串口之间的连接
	 * @param portName 串口名称
	 */
	public void closeSerialPort(String portName);
}
