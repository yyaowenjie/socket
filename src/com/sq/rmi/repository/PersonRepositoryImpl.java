package com.sq.rmi.repository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import com.sq.rmi.domain.PersonDomain;

/**
 * 此为远程对象的实现类，须继承UnicastRemoteObject
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午10:06:44
 */

public class PersonRepositoryImpl extends UnicastRemoteObject implements
		PersonRepository {

	private static final long serialVersionUID = 1L;

	public PersonRepositoryImpl() throws RemoteException {
		super();

	}

	public List<PersonDomain> GetList() throws RemoteException {

		System.out.println("Get Person Start!");
		List<PersonDomain> personList = new LinkedList<PersonDomain>();

		PersonDomain person1 = new PersonDomain();
		person1.setAge(25);
		person1.setId(0);
		person1.setName("Leslie");
		personList.add(person1);

		PersonDomain person2 = new PersonDomain();
		person2.setAge(25);
		person2.setId(1);
		person2.setName("Rose");
		personList.add(person2);

		return personList;
	}
}