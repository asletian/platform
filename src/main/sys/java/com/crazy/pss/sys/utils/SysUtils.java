package com.crazy.pss.sys.utils;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.crazy.pss.sys.model.Dictionary;
import com.crazy.pss.sys.model.Menu;
import com.crazy.pss.sys.service.DictionaryService;
import com.crazy.pss.sys.service.MenuService;

@Component
@Lazy(false)
public class SysUtils implements ApplicationContextAware{
	
	private static MenuService menuService;
	private static DictionaryService dictService;
	
	public static List<Menu> getMenuList(){
		return menuService.searchAll();
	}
	
	public static List<Menu> getMenusByTarget(String target){
		return menuService.searchByTarget(target);
	}

	public static List<Menu> getMenusByParent(String parentId){
		return menuService.searchByParent(parentId);
	}
	
	public static List<Menu> getTopMenus(){
		return menuService.searchTop();
	}
	
	public static List<Dictionary> getDictList(String type){
		List<Dictionary> ls = dictService.searchBy("type", type);
		return ls;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext){
		menuService = (MenuService) applicationContext.getBean("sys.menuService");
		dictService = (DictionaryService) applicationContext.getBean("sys.dictionaryService");
	}

}