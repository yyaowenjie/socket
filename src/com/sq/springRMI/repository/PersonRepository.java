package com.sq.springRMI.repository;

import java.util.List;

import com.sq.springRMI.domain.PersonEntity;

/**
 * ��ΪԶ�̶�����õĽӿڣ�����Spring����������̳�����
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time ����10:04:54
 */
public interface PersonRepository {
	
	List<PersonEntity> GetList();
	
	public int sum(int a, int b);
	
}