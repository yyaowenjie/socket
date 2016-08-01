package com.sq.rmi.repository;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import com.sq.rmi.domain.PersonDomain;

/**
 * ��ΪԶ�̶�����õĽӿڣ�����̳�Remote��
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time ����10:04:54
 */
public interface PersonRepository extends Remote {
	
	List<PersonDomain> GetList() throws RemoteException;
	
}