package com.crazy.pss.security.init;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;

import com.crazy.pss.security.annotation.Oper;
import com.crazy.pss.security.annotation.Res;
import com.crazy.pss.security.model.ControllerResource;
import com.crazy.pss.security.service.ControllerResourceService;

public class InitSecurityResource {
	
	private static Logger log = LoggerFactory.getLogger(InitSecurityResource.class);

	private ControllerResourceService controllerResourceService;
	
	/**
	 * 初始化Controller资源
	 * 
	 * @param locationPattern
	 */
	public void initControllerResDB(String locationPattern){
		Resource[] rs = null;
		try {
			
			Assert.notNull(locationPattern, "请注入properties文件路径");
			if(log.isInfoEnabled()){
				log.info("开始加载路径为【" + locationPattern + "】的属性文件");
			}
			
			//加载类资源文件
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			rs =  resolver.getResources(locationPattern);
			if(log.isInfoEnabled()){
				log.info("加载路径为【" + locationPattern + "】的属性文件成功");
			}
			
			if(rs != null && rs.length > 0) {
				
				//为了得到MetadataReader，先创建factory
				MetadataReaderFactory metaFactory = new CachingMetadataReaderFactory();
				
				//遍历类资源文件
				for (Resource resource : rs) {
					
					//读取指定的类的信息
					MetadataReader metadataReader = metaFactory.getMetadataReader(resource);
					
					//保存Controller类资源
					saveControllerResource(metadataReader, metaFactory, resolver);
				}
			}
			
			/**
			//建立资源之间的父子关系
			List<ControllerResource> allResources = controllerResourceService.searchAll();
			for(ControllerResource ar : allResources){
				//如果定义了parentSn，则根据这个parentSn查找父亲，并建立关联
				String parentSn = ar.getParentSn();
				if(parentSn != null && !parentSn.trim().equals("")){
					ControllerResource parent = controllerResourceService.searchUnique("sn", parentSn);
					if(parent != null){
						ar.setParent(parent);
					}
				}
			}
			**/
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error("加载路径为【" + locationPattern + "】的属性文件失败，失败原因：" + e.getMessage());
			}
		}
	}
	
	/**
	 * 保存资源
	 * 
	 * @param metadataReader
	 * @param metaFactory
	 * @param resolver
	 * @throws IOException
	 */
	private void saveControllerResource(MetadataReader metadataReader,
			MetadataReaderFactory metaFactory,ResourcePatternResolver resolver) throws IOException{
		
		//得到类的元数据描述信息
		ClassMetadata classMetadata =  metadataReader.getClassMetadata();
		
		//得到类的注解元数据
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		
		//是否存在@Res注解
		if(annotationMetadata.hasAnnotation(Res.class.getName())) {
			if(log.isInfoEnabled()) {
				log.info("扫描到类："+classMetadata.getClassName()+" 包含有@Res注解.");
			}
			
			//取出@Res注解的属性
			Map<String, Object> resAttrs = annotationMetadata.getAnnotationAttributes(Res.class.getName());
			
			//获得@Res注解中的sn属性的值
			String resSn = (String)resAttrs.get("sn");		
			
			//获得@Res注解中的name属性的值
			String resName = (String)resAttrs.get("name");	
			
			//获得@Res注解中的orderNumber属性的值
			int orderNumber = (Integer)resAttrs.get("orderNumber");
			
			//获得@Res注解中的parentSn属性的值
			String parentSn = (String)resAttrs.get("parentSn");			
			
			//获得@Res注解所在的类名
			String className = classMetadata.getClassName();
			
			//首先根据sn，查询数据库，如果已经存在，则不再创建，而是使用原有的ActionResource对象
			ControllerResource cr = controllerResourceService.searchUnique("sn", resSn);
			if(cr == null){
				//如果对应的资源尚不存在，则创建ActionResource对象
				cr = new ControllerResource();
			}

			cr.addClassName(className);
			cr.setName(resName);
			cr.setSn(resSn);
			cr.setOrderNumber(orderNumber);
			cr.setParentSn(parentSn);
			
			log.debug("扫描到资源【"+resSn+"("+resName+")】："+className);
			
			//搜索本类型下面定义了@Oper的方法及其父类型下面定义了@Oper的方法
			searchOperAnnotations(cr, metadataReader,metaFactory,resolver);
			
			controllerResourceService.save(cr);
		}
	}

	/**
	 * 搜索包含有@Oper注解的方法，除了搜索本类型，有父类，还需继续搜索父类中包含@Oper注解的方法
	 * 
	 * @param cr
	 * @param metadataReader
	 * @param metaFactory
	 * @param resolver
	 * @throws IOException
	 */
	private void searchOperAnnotations(ControllerResource cr,MetadataReader metadataReader,
			MetadataReaderFactory metaFactory,ResourcePatternResolver resolver) throws IOException{
		//得到类的注解元数据
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		//扫描这个类下面的方法，寻找包含@Oper注解的方法
		Set<MethodMetadata> methodMetas = annotationMetadata.getAnnotatedMethods(Oper.class.getName());
		for(MethodMetadata mmd:methodMetas){
			Map<String,Object> operAttrs = mmd.getAnnotationAttributes(Oper.class.getName());
			String methodName = mmd.getMethodName();
			String operName = (String)operAttrs.get("name");
			if(operName == null || operName.equals("")){ //未定义操作名
				operName = getDefaultOperName(methodName);
			}
			String operSn = (String)operAttrs.get("sn");
			if(operSn == null || operSn.equals("")){
				operSn = getDefaultOperSn(methodName);
			}
			int operIndex = (Integer)operAttrs.get("index");
			if(operIndex == -1){
				operIndex = getDefaultOperIndex(methodName);
			}
			
			cr.addControllerMethodOper(methodName, operName, operSn, operIndex);
			log.debug("扫描到操作【"+operSn+"("+operName+")】["+operIndex+"]："+methodName);
		}
		
		//如果有父类，而且不是java.lang.Object，则继续搜索这个父类中是否还包含有@Oper注解的方法
		if(metadataReader.getClassMetadata().hasSuperClass() && !metadataReader.getClassMetadata().getSuperClassName().equals(Object.class.getName())){
			//得到父类的名称，比如：cn.com.leadfar.oa.web.actions.PartyAction
			String superClassName = metadataReader.getClassMetadata().getSuperClassName();
			//构造父类的资源路径，比如：cn/com/leadfar/oa/web/actions/PartyAction.class
			String superClassPath = superClassName.replace('.', '/')+".class";
			org.springframework.core.io.Resource superClassResource = resolver.getResource(superClassPath);
			
			//递归搜索父类包含的操作
			searchOperAnnotations(cr, metaFactory.getMetadataReader(superClassResource), metaFactory,resolver);
		}
	}
	
	/**
	 * 根据方法名，得到缺省的操作名
	 * @param methodName
	 * @return
	 */
	private String getDefaultOperName(String methodName){
		if(methodName.startsWith("add")){
			return "添加";
		}else if(methodName.startsWith("update")){
			return "更新";
		}else if(methodName.startsWith("del")){
			return "删除";
		}else{
			return "查询";
		}
	}
	private String getDefaultOperSn(String methodName){
		if(methodName.startsWith("add")){
			return "CREATE";
		}else if(methodName.startsWith("update")){
			return "UPDATE";
		}else if(methodName.startsWith("del")){
			return "DELETE";
		}else{
			return "READ";
		}
	}
	private int getDefaultOperIndex(String methodName){
		if(methodName.startsWith("add")){
			return 0;
		}else if(methodName.startsWith("update")){
			return 1;
		}else if(methodName.startsWith("del")){
			return 2;
		}else{
			return 3;
		}
	}
}
