package com.sq.rxtx.receiver;

import gnu.io.SerialPort;

/**
 * 串行接口接收常量
 * 
 * @User yaowenjie
 * @Date 2016-7-28
 * @Time 上午11:49:51
 */
public interface SerialReaderConsts {
	
	/** 监听串口端口号 */
	public static final String port = "COM2";
	/** 监听串口端口频率 */
	public static final String rate = "57600";
	/** 数据位 */
	public static final String dataBit = "" + SerialPort.DATABITS_8;
	/** 停止位 */
	public static final String stopBit = "" + SerialPort.STOPBITS_1;
	/** 奇偶校验 */
	public static final String parity = "" + SerialPort.PARITY_NONE;
	
	int parityInt = SerialPort.PARITY_NONE;
	/** 端口读入数据事件触发后,等待n毫秒后再读取,以便让数据一次性读完 */
	/** 延时等待端口数据准备的时间 */
	public static final String PARAMS_DELAY = "delay read";
	/** 超时时间 */
	public static final String PARAMS_TIMEOUT = "timeout";
	/** 端口名称 */
	public static final String PARAMS_PORT = "port name"; //
	/** 数据位 */
	public static final String PARAMS_DATABITS = "data bits";
	/** 停止位 */
	public static final String PARAMS_STOPBITS = "stop bits"; //
	/** 奇偶校验 */
	public static final String PARAMS_PARITY = "parity";
	/** 波特率 */
	public static final String PARAMS_RATE = "rate";
}
