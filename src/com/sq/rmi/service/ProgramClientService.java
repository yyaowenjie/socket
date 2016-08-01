package com.sq.rmi.service;

import java.rmi.Naming;
import java.util.List;
import org.springframework.stereotype.Service;
import com.sq.rmi.domain.PersonDomain;
import com.sq.rmi.repository.PersonRepository;
/**
 * RMI�ͻ���
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time ����10:15:03
 */
@Service
public class ProgramClientService {
	
	public static void main(String[] args) {
		try {
			// ����Զ�̶���ע��RMI·����ӿڱ��������������һ��
			PersonRepository personService = (PersonRepository) Naming
					.lookup("rmi://127.0.0.1:6666/pService");
			List<PersonDomain> personList = personService.GetList();
			for (PersonDomain person : personList) {
				System.out.println("ID:" + person.getId() + " Age:"
						+ person.getAge() + " Name:" + person.getName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}