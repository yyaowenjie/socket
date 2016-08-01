package com.sq.rmi.domain;

import java.io.Serializable;

/**
 * 注意对象必须继承Serializable
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午10:03:34
 */
public class PersonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private int age;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}
}