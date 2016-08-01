package test.juint;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sq.rxtx.sender.SerialportCommRXTXImpl;

import test.base.TestCase;

public class PortDemoTest extends TestCase {
	@Autowired
	private SerialportCommRXTXImpl serialportCommRXTXImpl;

	@Test
	public void test() {
		this.serialportCommRXTXImpl.getAvailableSerialPorts();
		this.serialportCommRXTXImpl.openSerialPort("COM1");
		this.serialportCommRXTXImpl.sendMessage("打发1才知道");
	}
}
