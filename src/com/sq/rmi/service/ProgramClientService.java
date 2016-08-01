package com.sq.rmi.service;

import java.rmi.Naming;
import java.util.List;
import org.springframework.stereotype.Service;
import com.sq.rmi.domain.PersonDomain;
import com.sq.rmi.repository.PersonRepository;
/**
 * RMI客户端
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 上午10:15:03
 */
@Service
public class ProgramClientService {
	
	public static void main(String[] args) {
		try {
			// 调用远程对象，注意RMI路径与接口必须与服务器配置一致
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