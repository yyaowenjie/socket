package com.sq.rxtx.sender;

/**
 * ����ͨѶ����
 * 
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time ����4:54:16
 */
public interface SerialportCommConsts {
	
	public static final String SERIALPORT_RATE = "57600";

	public static final String PARAMS_DELAY = "delay read"; // ��ʱ�ȴ��˿�����׼����ʱ��
	
    public static final String PARAMS_TIMEOUT = "timeout"; // ��ʱʱ��
    
    public static final String PARAMS_PORT = "port name"; // �˿�����
    
    public static final String PARAMS_DATABITS = "data bits"; // ����λ
    
    public static final String PARAMS_STOPBITS = "stop bits"; // ֹͣλ
    
    public static final String PARAMS_PARITY = "parity"; // ��żУ��
    
    public static final String PARAMS_RATE = "rate"; // ������
}
