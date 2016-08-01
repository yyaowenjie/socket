package com.sq.rxtx.sender;

/**
 * 串口通讯常量
 * 
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time 下午4:54:16
 */
public interface SerialportCommConsts {
	
	public static final String SERIALPORT_RATE = "57600";

	public static final String PARAMS_DELAY = "delay read"; // 延时等待端口数据准备的时间
	
    public static final String PARAMS_TIMEOUT = "timeout"; // 超时时间
    
    public static final String PARAMS_PORT = "port name"; // 端口名称
    
    public static final String PARAMS_DATABITS = "data bits"; // 数据位
    
    public static final String PARAMS_STOPBITS = "stop bits"; // 停止位
    
    public static final String PARAMS_PARITY = "parity"; // 奇偶校验
    
    public static final String PARAMS_RATE = "rate"; // 波特率
}
