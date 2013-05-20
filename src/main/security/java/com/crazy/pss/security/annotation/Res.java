package com.crazy.pss.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Description 定义资源的注解
 * 
 * @author crazy/Y
 * @date 2013-5-16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Res {

	/**
	 * 资源名
	 * 
	 * @return 资源名
	 */
	String name();
	
	/**
	 * 资源唯一标示符
	 * 
	 * @return 资源唯一标示符
	 */
	String sn();
	
	/**
	 * 父资源唯一标示符
	 * 
	 * @return 父资源唯一标示符
	 */
	String parentSn() default "";
	
	/**
	 * 资源排序值
	 * 
	 * @return 资源排序值
	 */
	int orderNumber() default 0;
}
