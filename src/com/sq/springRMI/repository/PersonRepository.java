package com.sq.springRMI.repository;

import java.util.List;

import com.sq.springRMI.domain.PersonEntity;

/**
 * 此为远程对象调用的接口，配置Spring环境，无需继承其他
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午10:04:54
 */
public interface PersonRepository {
	
	List<PersonEntity> GetList();
	
	public int sum(int a, int b);
	
}