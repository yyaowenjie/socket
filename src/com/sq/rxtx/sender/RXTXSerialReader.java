package com.sq.rxtx.sender;

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
import org.springframework.stereotype.Component;


/**
 * �������ݼ���������Ҫ�������ݵĴ���
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time ����4:53:06
 */
@Component
public class RXTXSerialReader extends Observable implements Runnable, SerialPortEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(RXTXSerialReader.class);
	
	public static CommPortIdentifier portId;

	/**  �����б���� */
	@SuppressWarnings("rawtypes")
	public static Enumeration portList;

	/**   ���������� */
	public InputStream inputStream;

	/**   ��������� */
	public OutputStream outputStream;
	
	/**  ���ж˿ں� */
	public static SerialPort serialPort;

	/**   ���߳� */
	public Thread readThread;

	/**   �˿��Ƿ���� */
	private boolean isOpen = false;

	/**   ���ڳ�ʼ������ */
	@SuppressWarnings("rawtypes")
	public HashMap serialParams;

	public int delayRead = 100;

	/**   buffer�е�ʵ�������ֽ��� */
	public int numBytes;

	/**   4k��buffer�ռ�,���洮�ڶ�������� */
	private static byte[] readBuffer = new byte[1024];

	public RXTXSerialReader() {
		isOpen = false;
	}

	/**
	 * �Ķ˿����Ƿ��Ѵ�����
	 * 
	 * @return boolean
	 */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * ��дrun����
	 */
	public void run() {

	}
	
	/**
	 * serialEvent����������������
	 */
	public void serialEvent(SerialPortEvent event) {
		try {
			Thread.sleep(delayRead);
		} catch (InterruptedException e) {
			log.error("sleep�쳣,������������ˣ�");
		}
		switch (event.getEventType()) {
		case SerialPortEvent.BI:/* Break interrupt,ͨѶ�ж� */
		case SerialPortEvent.OE:/* Overrun error����λ���� */
		case SerialPortEvent.FE:/* Framing error����֡���� */
		case SerialPortEvent.PE:/* Parity error��У����� */
		case SerialPortEvent.CD:/* Carrier detect���ز���� */
		case SerialPortEvent.CTS:/* Clear to send��������� */
		case SerialPortEvent.DSR:/* Data set ready�������豸���� */
		case SerialPortEvent.RI:/* Ring indicator������ָʾ */
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:// ������������
			break;
		case SerialPortEvent.DATA_AVAILABLE: // �˿��п������ݡ�������������
		
			try {
				/**   ��ζ�ȡ,���������ݶ��� */
				while (inputStream.available() > 0) {
					numBytes = inputStream.read(readBuffer);
				}
				/**   ��ӡ���յ����ֽ����ݵ�ASCII��  */
				for (int i = 0; i < numBytes; i++) {
					System.out.println("msg[" + numBytes + "]: ["
							+ readBuffer[i] + "]:" + (char) readBuffer[i]);
				}
				numBytes = inputStream.read(readBuffer);
				changeMessage(readBuffer, numBytes);
			} catch (IOException e) {
				log.error("��ȡbyte�����쳣");
			}
			break;
		}
	}

	/**
	 * ͨ��observer pattern���յ������ݷ��͸�observer ��buffer�еĿ��ֽ�ɾ�����ٷ��͸�����Ϣ,֪ͨ�۲���
	 * 
	 * @param message
	 *            ��Ϣ�ı�
	 * @param length
	 *            �ı�����
	 */
	public void changeMessage(byte[] message, int length) {
		setChanged();
		byte[] temp = new byte[length];
		System.arraycopy(message, 0, temp, 0, length);
		notifyObservers(temp);
	}

	/**
	 * �رմ��� 
	 */
	public void close() {
		if (isOpen) {
			try {
				serialPort.notifyOnDataAvailable(false);
				serialPort.removeEventListener();
				inputStream.close();
				serialPort.close();
				isOpen = false;
			} catch (IOException ex) {
				log.error("�رմ����쳣��");
			}
		}
	}

	/**
	 * �򿪴��ڸ��ݳ�ʼ������ 
	 * 
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void open(HashMap params) {
		serialParams = params;
		if (isOpen) {
			close();
		}
		try {
			/**   ������ʼ�� */
			int timeout = Integer.parseInt(serialParams.get(
					SerialportCommConsts.PARAMS_TIMEOUT).toString());
			int rate = Integer.parseInt(serialParams.get(
					SerialportCommConsts.PARAMS_RATE).toString());
			int dataBits = Integer.parseInt(serialParams.get(
					SerialportCommConsts.PARAMS_DATABITS).toString());
			int stopBits = Integer.parseInt(serialParams.get(
					SerialportCommConsts.PARAMS_STOPBITS).toString());
			int parity = Integer.parseInt(serialParams.get(
					SerialportCommConsts.PARAMS_PARITY).toString());
			delayRead = Integer.parseInt(serialParams.get(
					SerialportCommConsts.PARAMS_DELAY).toString());
			String port = serialParams.get(SerialportCommConsts.PARAMS_PORT)
					.toString();

			/**   �򿪶˿� */
			portId = CommPortIdentifier.getPortIdentifier(port);
			serialPort = (SerialPort) portId.open("SerialReader", timeout);
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
			isOpen = true;
		} catch (PortInUseException e) {
			log.error("�˿�"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"�Ѿ���ռ��" + e);
		} catch (TooManyListenersException e) {
			log.error("�˿�"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"�����߹���");
		} catch (UnsupportedCommOperationException e) {
			log.error("�˿ڲ������֧��");
		} catch (NoSuchPortException e) {
			log.error("�˿�"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"������");
		} catch (IOException e) {
			log.error("�򿪶˿�"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"ʧ��");
		}
		serialParams.clear();
	}

	public static String getPortTypeName(int portType) {
		switch (portType) {
		case CommPortIdentifier.PORT_I2C:
			return "I2C";
		case CommPortIdentifier.PORT_PARALLEL:
			return "Parallel";
		case CommPortIdentifier.PORT_RAW:
			return "Raw";
		case CommPortIdentifier.PORT_RS485:
			return "RS485";
		case CommPortIdentifier.PORT_SERIAL:
			return "Serial";
		default:
			return "unknown type";
		}
	}

	/**
	 * readThread�߳����� 
	 */
	public void start() {
		try {
			outputStream = serialPort.getOutputStream();
			readThread = new Thread(this);
			readThread.start();
		} catch (Exception e) {
			log.error("����readThread�쳣��");
		}
	}

	/**
	 * �򴮿ڷ������� 
	 * 
	 * @param message
	 *            ��Ҫ���͵ı�������
	 */
	public void run(String message) {
		try {
			if (message != null && message.length() != 0) {
				/**   �����ڷ������ݣ���˫��ͨѶ�ġ� */
				outputStream.write(message.getBytes());
			}
		} catch (IOException e) {
			log.error("�����ڷ��������쳣��" + e);
		}
	}

	
}
