package com.sq.socket.client;

/**
 * Socket��Ϣ��װ��
 * 
 * @User yaowenjie
 * @Date 2016-6-22
 * @Time ����1:55:25
 */
public class SocketClientInfoDomain {
	
	private Integer _socket_port ;
	
	private String _server_host;
	
//	/** ���ó�˽������,���쵥��ģʽ */
//	private SocketClientInfoDomain(){
//		
//	}
	public SocketClientInfoDomain(){
		
	}
	
	public Integer get_socket_port() {
		return _socket_port;
	}

	public void set_socket_port(Integer _socket_port) {
		this._socket_port = _socket_port;
	}

	public String get_server_host() {
		return _server_host;
	}

	public void set_server_host(String _server_host) {
		this._server_host = _server_host;
	}
	
	
}
