package com.crazy.pss.common.config;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * 
 * @Description 全局配置类
 * 
 * @author crazy/Y
 * @date 2013-4-6
 */
@Component
@Lazy(false)
public class GlobalConfiguration{
	
	private static Logger log = LoggerFactory.getLogger(GlobalConfiguration.class);
	
	private static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	
	private static Properties cache = new Properties();
	
	private GlobalConfiguration(){
		loadCache("classpath*:/application.properties");
	};
	
	/**
	 * 属性文件加载对象PathMatchingResourcePatternResolver
	 */
	private static Resource[] loadFile(String locationPattern) {
		Resource[] rs = null;
		try {
			Assert.notNull(locationPattern, "请注入properties文件路径");
			if(log.isInfoEnabled()){
				log.info("开始加载路径为【" + locationPattern + "】的属性文件");
			}
			rs =  resolver.getResources(locationPattern);
			if(log.isInfoEnabled()){
				log.info("加载路径为【" + locationPattern + "】的属性文件成功");
			}
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error("加载路径为【" + locationPattern + "】的属性文件失败，失败原因：" + e.getMessage());
			}
		}
		return rs;
	}
	
	private static void loadCache(String locationPattern) {
		Resource[] rs = loadFile(locationPattern);
		try {
			Assert.notNull(rs, "没有获取到任何资源文件");
			if(rs != null && rs.length > 0){
				for (Resource resource : rs) {
					cache.load(resource.getInputStream());
				}
			}
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error("加载路径为【" + locationPattern + "】的属性文件失败，失败原因：" + e.getMessage());
			}
		}
	}
	
	public static String getConfig(String key){
		return cache.getProperty(key);
	}
	
}
