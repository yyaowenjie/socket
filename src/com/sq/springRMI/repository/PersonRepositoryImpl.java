package com.sq.springRMI.repository;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.sq.springRMI.domain.PersonEntity;

/**
 * 此为远程对象的实现类
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午10:06:44
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

	public PersonRepositoryImpl() {
		super();
	}

	public int sum(int a, int b) {
		System.out.println("server"+ (a-b));
		return a + b;
	}

	public List<PersonEntity> GetList() {
		System.out.println("Get Person Start!");
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			
		}
		List<PersonEntity> personList = new LinkedList<PersonEntity>();

		PersonEntity person1 = new PersonEntity();
		person1.setAge(25);
		person1.setId(0);
		person1.setName("Leslie");
		personList.add(person1);

		PersonEntity person2 = new PersonEntity();
		person2.setAge(25);
		person2.setId(1);
		person2.setName("Rose");
		personList.add(person2);

		return personList;
	}
}