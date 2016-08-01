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
 * �������˿�
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time ����10:08:32
 */
@Service
public class ProgramServerService {
	
	private static final Logger log = LoggerFactory.getLogger(ProgramServerService.class);

	public static void main(String[] args) {
		try {
			PersonRepository person = new PersonRepositoryImpl();
			// ע��ͨѶ�˿�
			LocateRegistry.createRegistry(6666);
			// ע��ͨѶ·��
			Naming.rebind("rmi://127.0.0.1:6666/pService", person);
			System.out.println("Service Start!");
		} catch (Exception e) {
			log.error("�������˶˿��쳣��",e);
		}
	}
}