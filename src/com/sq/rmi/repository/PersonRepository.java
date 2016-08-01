package com.sq.rmi.repository;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import com.sq.rmi.domain.PersonDomain;

/**
 * 此为远程对象调用的接口，必须继承Remote类
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午10:04:54
 */
public interface PersonRepository extends Remote {
	
	List<PersonDomain> GetList() throws RemoteException;
	
}