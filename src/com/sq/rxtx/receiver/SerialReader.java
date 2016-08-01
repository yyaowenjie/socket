package com.sq.rxtx.receiver;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Observable;
import java.util.TooManyListenersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ����Server���,���մ����byte����
 * 
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time ����5:11:12
 */
public class SerialReader extends Observable implements Runnable, SerialPortEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(SerialReader.class);

	public static CommPortIdentifier portId;
	
	/** sleeptime*/
	public int delayRead = 100;
	
	/** buffer�е�ʵ�������ֽ��� */
	public int numBytes; 
	
	/**  4k��buffer�ռ�,���洮�ڶ�������� */
	private static byte[] readBuffer = new byte[1024]; 
	
	/**  �����б���� */
	@SuppressWarnings("rawtypes")
	public static Enumeration portList;
	
	public InputStream inputStream;
	
	public OutputStream outputStream;
	
	/**  ���ж˿ں� */
	public static SerialPort serialPort;
	
	/**   ���ڳ�ʼ������ */
	@SuppressWarnings("rawtypes")
	public HashMap serialParams;
	
	/**  ������static���͵�  */
	public Thread readThread;
	
	/**   �˿��Ƿ����  */
	public boolean isOpen = false;

	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * ��ʼ���˿ڲ����Ĳ���.
	 * 
	 * @throws SerialPortException
	 * 
	 * @see
	 */
	public SerialReader() {
		isOpen = false;
	}
	@SuppressWarnings("rawtypes")
	public void open(HashMap params) {
		serialParams = params;
		if (isOpen) {
			close();
		}
		try {
			// ������ʼ��
			int timeout = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_TIMEOUT) .toString());
			int rate = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_RATE) .toString());
			int dataBits = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_DATABITS) .toString());
			int stopBits = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_STOPBITS) .toString());
			int parity = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_PARITY) .toString());
			delayRead = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_DELAY) .toString());
			String port = serialParams.get(SerialReaderConsts.PARAMS_PORT).toString();
			// �򿪶˿�
			portId = CommPortIdentifier.getPortIdentifier(port);
			serialPort = (SerialPort) portId.open("SerialReader", timeout);
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
			isOpen = true;
		} catch (PortInUseException e) {
			log.error("�˿�"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"�Ѿ���ռ��" + e);
		} catch (TooManyListenersException e) {
			log.error("�˿�"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"�����߹���");
		} catch (UnsupportedCommOperationException e) {
			log.error("�˿ڲ������֧��");
		} catch (NoSuchPortException e) {
			log.error("�˿�"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"������");
		} catch (IOException e) {
			log.error("�򿪶˿�"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"ʧ��");
		}
		serialParams.clear();
		Thread readThread = new Thread(this);
		readThread.start();
	}

	public void run() {
		
	}

	public void start() {
		try {
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
		}
		try {
			readThread = new Thread(this);
			readThread.start();
		} catch (Exception e) {
			log.error("start ���нӿ�ʧ��" + e);
		}
	}

	public void run(String message) {

		try {
			if (message != null && message.length() != 0) {
				// �����ڷ������ݣ���˫��ͨѶ�ġ�
				outputStream.write(message.getBytes()); 
			}
		} catch (IOException e) {
			log.error("����Messageʧ��" + e);
		}
	}

	public void close() {
		if (isOpen) {
			try {
				serialPort.notifyOnDataAvailable(false);
				serialPort.removeEventListener();
				inputStream.close();
				serialPort.close();
				isOpen = false;
			} catch (IOException ex) {
				log.error("�رմ��нӿ�ʧ�ܣ�");
			}
		}
	}

	public void serialEvent(SerialPortEvent event) {
		try {
			Thread.sleep(delayRead);
		} catch (InterruptedException e) {
			log.error("Sleepʧ��,�����ܣ�");
		}
		switch (event.getEventType()) {
		case SerialPortEvent.BI:/** Break interrupt,ͨѶ�ж� */
		case SerialPortEvent.OE:/** Overrun error����λ���� */
		case SerialPortEvent.FE:/** Framing error����֡���� */
		case SerialPortEvent.PE:/** Parity error��У����� */
		case SerialPortEvent.CD:/** Carrier detect���ز���� */
		case SerialPortEvent.CTS:/** Clear to send��������� */
		case SerialPortEvent.DSR:/** Data set ready�������豸���� */
		case SerialPortEvent.RI:/** Ring indicator������ָʾ */
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:// ������������
			break;
		case SerialPortEvent.DATA_AVAILABLE: // �˿��п������ݡ�������������
			try {
				/** ��ζ�ȡ,���������ݶ��� */
				while (inputStream.available() > 0) {
					numBytes = inputStream.read(readBuffer);
				}
				log.error("�����ν��յ��ֽ���Ϊ:" + numBytes);
				String str = new String(readBuffer,"GBK");
				log.error("�����ν��յ�����Ϊ:" + str);
				numBytes = inputStream.read(readBuffer);
				SerialReader.this.changeMessage(readBuffer, numBytes);
			} catch (IOException e) {
				log.error("��ȡ�ӿ����ݴ���:" + e);
			}
			break;
		}
	}

	/**
	 * ͨ��observer pattern���յ������ݷ��͸�observer
	 * ��buffer�еĿ��ֽ�ɾ�����ٷ��͸�����Ϣ,֪ͨ�۲���
	 * @param message
	 * @param length
	 */
	public void changeMessage(byte[] message, int length) {
		setChanged();
		byte[] temp = new byte[length];
		System.arraycopy(message, 0, temp, 0, length);
		notifyObservers(temp);
	}

	
	/**
	 * �򿪴��ж˿�
	 * @param message
	 */
	@SuppressWarnings("rawtypes")
	public void openSerialPort(String message) {
		HashMap<String, Comparable> params = new HashMap<String, Comparable>();
		params.put(SerialReaderConsts.PARAMS_PORT, SerialReaderConsts.port); // �˿�����
		params.put(SerialReaderConsts.PARAMS_RATE, SerialReaderConsts.rate); // ������
		params.put(SerialReaderConsts.PARAMS_DATABITS, SerialReaderConsts.dataBit); // ����λ
		params.put(SerialReaderConsts.PARAMS_STOPBITS, SerialReaderConsts.stopBit); // ֹͣλ
		params.put(SerialReaderConsts.PARAMS_PARITY, SerialReaderConsts.parityInt); // ����żУ��
		params.put(SerialReaderConsts.PARAMS_TIMEOUT, 100); // �豸��ʱʱ�� 1��
		params.put(SerialReaderConsts.PARAMS_DELAY, 100); // �˿�����׼��ʱ�� 1��
		log.error(message);
		try {
			SerialReader.this.open(params);
			if (message != null && message.length() != 0) {
				SerialReader.this.start();
				SerialReader.this.run(message);
			}
		} catch (Exception e) {
			log.error("�򿪴��нӿڶ˿�ʧ�ܣ�" + e);
		}
	}

}

