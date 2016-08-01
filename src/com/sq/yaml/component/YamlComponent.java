package com.sq.yaml.component;


import org.ho.yaml.Yaml;
import org.springframework.stereotype.Component;

/**
 * Yaml ʵ�ù��߿������
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time ����11:27:07
 */
@Component
public class YamlComponent {
	/**
	 * ���л�����Yaml������
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public static <T> String DomainSerializationYaml(T entity){
		String yaml = Yaml.dump(entity);
		return yaml;
	}
	
	/**
	 * Yaml���Է����л���ʵ�������
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T YamlSerializationDomain(String yaml,T entity){
		T t = (T) Yaml.loadType(yaml,entity.getClass());
		return t;
	}
}
