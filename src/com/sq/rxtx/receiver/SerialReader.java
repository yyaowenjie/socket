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
 * 串口Server组件,接收传入的byte数据
 * 
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time 下午5:11:12
 */
public class SerialReader extends Observable implements Runnable, SerialPortEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(SerialReader.class);

	public static CommPortIdentifier portId;
	
	/** sleeptime*/
	public int delayRead = 100;
	
	/** buffer中的实际数据字节数 */
	public int numBytes; 
	
	/**  4k的buffer空间,缓存串口读入的数据 */
	private static byte[] readBuffer = new byte[1024]; 
	
	/**  串口列表对象 */
	@SuppressWarnings("rawtypes")
	public static Enumeration portList;
	
	public InputStream inputStream;
	
	public OutputStream outputStream;
	
	/**  串行端口号 */
	public static SerialPort serialPort;
	
	/**   串口初始化对象 */
	@SuppressWarnings("rawtypes")
	public HashMap serialParams;
	
	/**  本来是static类型的  */
	public Thread readThread;
	
	/**   端口是否打开了  */
	public boolean isOpen = false;

	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * 初始化端口操作的参数.
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
			// 参数初始化
			int timeout = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_TIMEOUT) .toString());
			int rate = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_RATE) .toString());
			int dataBits = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_DATABITS) .toString());
			int stopBits = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_STOPBITS) .toString());
			int parity = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_PARITY) .toString());
			delayRead = Integer.parseInt(serialParams.get(SerialReaderConsts.PARAMS_DELAY) .toString());
			String port = serialParams.get(SerialReaderConsts.PARAMS_PORT).toString();
			// 打开端口
			portId = CommPortIdentifier.getPortIdentifier(port);
			serialPort = (SerialPort) portId.open("SerialReader", timeout);
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
			isOpen = true;
		} catch (PortInUseException e) {
			log.error("端口"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"已经被占用" + e);
		} catch (TooManyListenersException e) {
			log.error("端口"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"监听者过多");
		} catch (UnsupportedCommOperationException e) {
			log.error("端口操作命令不支持");
		} catch (NoSuchPortException e) {
			log.error("端口"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"不存在");
		} catch (IOException e) {
			log.error("打开端口"+serialParams.get( SerialReaderConsts.PARAMS_PORT ).toString()+"失败");
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
			log.error("start 串行接口失败" + e);
		}
	}

	public void run(String message) {

		try {
			if (message != null && message.length() != 0) {
				// 往串口发送数据，是双向通讯的。
				outputStream.write(message.getBytes()); 
			}
		} catch (IOException e) {
			log.error("发送Message失败" + e);
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
				log.error("关闭串行接口失败！");
			}
		}
	}

	public void serialEvent(SerialPortEvent event) {
		try {
			Thread.sleep(delayRead);
		} catch (InterruptedException e) {
			log.error("Sleep失败,不可能！");
		}
		switch (event.getEventType()) {
		case SerialPortEvent.BI:/** Break interrupt,通讯中断 */
		case SerialPortEvent.OE:/** Overrun error，溢位错误 */
		case SerialPortEvent.FE:/** Framing error，传帧错误 */
		case SerialPortEvent.PE:/** Parity error，校验错误 */
		case SerialPortEvent.CD:/** Carrier detect，载波检测 */
		case SerialPortEvent.CTS:/** Clear to send，清除发送 */
		case SerialPortEvent.DSR:/** Data set ready，数据设备就绪 */
		case SerialPortEvent.RI:/** Ring indicator，响铃指示 */
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:// 输出缓冲区清空
			break;
		case SerialPortEvent.DATA_AVAILABLE: // 端口有可用数据。读到缓冲数组
			try {
				/** 多次读取,将所有数据读入 */
				while (inputStream.available() > 0) {
					numBytes = inputStream.read(readBuffer);
				}
				log.error("本批次接收到字节数为:" + numBytes);
				String str = new String(readBuffer,"GBK");
				log.error("本批次接收到数据为:" + str);
				numBytes = inputStream.read(readBuffer);
				SerialReader.this.changeMessage(readBuffer, numBytes);
			} catch (IOException e) {
				log.error("读取接口数据错误:" + e);
			}
			break;
		}
	}

	/**
	 * 通过observer pattern将收到的数据发送给observer
	 * 将buffer中的空字节删除后再发送更新消息,通知观察者
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
	 * 打开串行端口
	 * @param message
	 */
	@SuppressWarnings("rawtypes")
	public void openSerialPort(String message) {
		HashMap<String, Comparable> params = new HashMap<String, Comparable>();
		params.put(SerialReaderConsts.PARAMS_PORT, SerialReaderConsts.port); // 端口名称
		params.put(SerialReaderConsts.PARAMS_RATE, SerialReaderConsts.rate); // 波特率
		params.put(SerialReaderConsts.PARAMS_DATABITS, SerialReaderConsts.dataBit); // 数据位
		params.put(SerialReaderConsts.PARAMS_STOPBITS, SerialReaderConsts.stopBit); // 停止位
		params.put(SerialReaderConsts.PARAMS_PARITY, SerialReaderConsts.parityInt); // 无奇偶校验
		params.put(SerialReaderConsts.PARAMS_TIMEOUT, 100); // 设备超时时间 1秒
		params.put(SerialReaderConsts.PARAMS_DELAY, 100); // 端口数据准备时间 1秒
		log.error(message);
		try {
			SerialReader.this.open(params);
			if (message != null && message.length() != 0) {
				SerialReader.this.start();
				SerialReader.this.run(message);
			}
		} catch (Exception e) {
			log.error("打开串行接口端口失败！" + e);
		}
	}

}

