package com.sq.yaml.component;


import org.ho.yaml.Yaml;
import org.springframework.stereotype.Component;

/**
 * Yaml 实用工具控制组件
 * 
 * @User yaowenjie
 * @Date 2016-7-8
 * @Time 上午11:27:07
 */
@Component
public class YamlComponent {
	/**
	 * 序列化对象到Yaml语言中
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public static <T> String DomainSerializationYaml(T entity){
		String yaml = Yaml.dump(entity);
		return yaml;
	}
	
	/**
	 * Yaml语言反序列化到实体对象中
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
