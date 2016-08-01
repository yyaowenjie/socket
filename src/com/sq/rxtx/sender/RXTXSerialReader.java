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
 * 串口数据监控组件，主要负责数据的传输
 * @User yaowenjie
 * @Date 2016-7-27
 * @Time 下午4:53:06
 */
@Component
public class RXTXSerialReader extends Observable implements Runnable, SerialPortEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(RXTXSerialReader.class);
	
	public static CommPortIdentifier portId;

	/**  串口列表对象 */
	@SuppressWarnings("rawtypes")
	public static Enumeration portList;

	/**   串口输入流 */
	public InputStream inputStream;

	/**   串口输出流 */
	public OutputStream outputStream;
	
	/**  串行端口号 */
	public static SerialPort serialPort;

	/**   读线程 */
	public Thread readThread;

	/**   端口是否打开了 */
	private boolean isOpen = false;

	/**   串口初始化对象 */
	@SuppressWarnings("rawtypes")
	public HashMap serialParams;

	public int delayRead = 100;

	/**   buffer中的实际数据字节数 */
	public int numBytes;

	/**   4k的buffer空间,缓存串口读入的数据 */
	private static byte[] readBuffer = new byte[1024];

	public RXTXSerialReader() {
		isOpen = false;
	}

	/**
	 * 改端口上是否已打开连接
	 * 
	 * @return boolean
	 */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * 重写run方法
	 */
	public void run() {

	}
	
	/**
	 * serialEvent监听串口数据流量
	 */
	public void serialEvent(SerialPortEvent event) {
		try {
			Thread.sleep(delayRead);
		} catch (InterruptedException e) {
			log.error("sleep异常,见到这个就神了！");
		}
		switch (event.getEventType()) {
		case SerialPortEvent.BI:/* Break interrupt,通讯中断 */
		case SerialPortEvent.OE:/* Overrun error，溢位错误 */
		case SerialPortEvent.FE:/* Framing error，传帧错误 */
		case SerialPortEvent.PE:/* Parity error，校验错误 */
		case SerialPortEvent.CD:/* Carrier detect，载波检测 */
		case SerialPortEvent.CTS:/* Clear to send，清除发送 */
		case SerialPortEvent.DSR:/* Data set ready，数据设备就绪 */
		case SerialPortEvent.RI:/* Ring indicator，响铃指示 */
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:// 输出缓冲区清空
			break;
		case SerialPortEvent.DATA_AVAILABLE: // 端口有可用数据。读到缓冲数组
		
			try {
				/**   多次读取,将所有数据读入 */
				while (inputStream.available() > 0) {
					numBytes = inputStream.read(readBuffer);
				}
				/**   打印接收到的字节数据的ASCII码  */
				for (int i = 0; i < numBytes; i++) {
					System.out.println("msg[" + numBytes + "]: ["
							+ readBuffer[i] + "]:" + (char) readBuffer[i]);
				}
				numBytes = inputStream.read(readBuffer);
				changeMessage(readBuffer, numBytes);
			} catch (IOException e) {
				log.error("读取byte数据异常");
			}
			break;
		}
	}

	/**
	 * 通过observer pattern将收到的数据发送给observer 将buffer中的空字节删除后再发送更新消息,通知观察者
	 * 
	 * @param message
	 *            消息文本
	 * @param length
	 *            文本长度
	 */
	public void changeMessage(byte[] message, int length) {
		setChanged();
		byte[] temp = new byte[length];
		System.arraycopy(message, 0, temp, 0, length);
		notifyObservers(temp);
	}

	/**
	 * 关闭串口 
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
				log.error("关闭串口异常！");
			}
		}
	}

	/**
	 * 打开串口根据初始化参数 
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
			/**   参数初始化 */
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

			/**   打开端口 */
			portId = CommPortIdentifier.getPortIdentifier(port);
			serialPort = (SerialPort) portId.open("SerialReader", timeout);
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
			isOpen = true;
		} catch (PortInUseException e) {
			log.error("端口"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"已经被占用" + e);
		} catch (TooManyListenersException e) {
			log.error("端口"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"监听者过多");
		} catch (UnsupportedCommOperationException e) {
			log.error("端口操作命令不支持");
		} catch (NoSuchPortException e) {
			log.error("端口"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"不存在");
		} catch (IOException e) {
			log.error("打开端口"+serialParams.get( SerialportCommConsts.PARAMS_PORT ).toString()+"失败");
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
	 * readThread线程启动 
	 */
	public void start() {
		try {
			outputStream = serialPort.getOutputStream();
			readThread = new Thread(this);
			readThread.start();
		} catch (Exception e) {
			log.error("启动readThread异常！");
		}
	}

	/**
	 * 向串口发送数据 
	 * 
	 * @param message
	 *            需要发送的报文数据
	 */
	public void run(String message) {
		try {
			if (message != null && message.length() != 0) {
				/**   往串口发送数据，是双向通讯的。 */
				outputStream.write(message.getBytes());
			}
		} catch (IOException e) {
			log.error("往串口发送数据异常！" + e);
		}
	}

	
}
