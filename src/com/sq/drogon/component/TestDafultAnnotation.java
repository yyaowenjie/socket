package com.sq.drogon.component;

import java.lang.annotation.Annotation;

import org.springframework.core.annotation.AnnotationUtils;
/**
 * 
 * @User yaowenjie
 * @Date 2016-7-29
 * @Time 上午9:36:03
 */
@DafultCallInterface
public class TestDafultAnnotation {
	@SuppressWarnings("all")
	public void test() {
		DafultCallInterface ma = TestDafultAnnotation.class
				.getAnnotation(DafultCallInterface.class);
		System.out.println(ma.countCallInterface());

		// 获取自身和从父类继承的注解
		Annotation[] annotations = TestDafultAnnotation.class.getAnnotations();
		// 仅获取自身的注解
		Annotation[] annotations1 = TestDafultAnnotation.class
				.getDeclaredAnnotations();
		DafultCallInterface requestMapping = AnnotationUtils.findAnnotation(
				getClass(), DafultCallInterface.class);
		if (requestMapping != null) {
			String currentViewPrefix = requestMapping.countCallInterface();
			System.out.println("123" + currentViewPrefix);
		}
	}

	public static void main(String[] args) {
		new TestDafultAnnotation().test();
	}
}