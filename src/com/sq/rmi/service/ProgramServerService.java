package com.sq.rmi.service;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sq.quert.service.SchedulerExecuteService;
import com.sq.rmi.repository.PersonRepository;
import com.sq.rmi.repository.PersonRepositoryImpl;

/**
 * 服务器端口
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午10:08:32
 */
@Service
public class ProgramServerService {
	
	private static final Logger log = LoggerFactory.getLogger(ProgramServerService.class);

	public static void main(String[] args) {
		try {
			PersonRepository person = new PersonRepositoryImpl();
			// 注册通讯端口
			LocateRegistry.createRegistry(6666);
			// 注册通讯路径
			Naming.rebind("rmi://127.0.0.1:6666/pService", person);
			System.out.println("Service Start!");
		} catch (Exception e) {
			log.error("服务器端端口异常！",e);
		}
	}
}