package com.crazy.pss.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.common.web.BaseController;
import com.crazy.pss.sys.model.Menu;
import com.crazy.pss.sys.service.MenuService;

/**
 * 
 * @Description 菜单
 * 
 * @author crazy/Y
 * @date 2013-4-8
 */
@Controller("sys.menuController")
@RequestMapping(value = Constants.ADMIN_PATH+"/sys/menu")
public class MenuController extends BaseController{

	@Resource(name = "sys.menuService")
	private MenuService menuService;
	
	@ModelAttribute
	public Menu get(@RequestParam(required=false) String id) {
		if (!StringUtils.isEmpty(id)){
			return menuService.get(id);
		}else{
			return new Menu();
		}
	}
	
	@RequestMapping(value = "form")
	public String form(Model model, Menu menu){
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu());
		} else {
			menu.setParent(menuService.get(menu.getParent().getId()));
		}
		model.addAttribute("menu", menu);
		return "modules/sys/menuForm";
	}
	
	@RequestMapping(value = "save")
	public String save(Model model, Menu menu){
		if(!beanValidator(model, menu)) {
			return form(model, menu);
		}
		menuService.save(menu);
		addMessage(model, "保存菜单" + menu.getName() + "成功");
		return list(model);
	}
	
	@RequestMapping(value = "delete")
	public String delete(Model model, Menu menu, RedirectAttributes redirectAttributes){
		menuService.delete(menu);
		addMessage(redirectAttributes, "删除菜单" + menu.getName() + "成功");
		return list(model);
	}
	
	@RequestMapping(value = "tree")
	public String tree(Model model,
			@RequestParam(value = "parentId", required = true)String parentId){
		List<Menu> menus = menuService.searchByParent(parentId);
		model.addAttribute("menus", menus);
		return "modules/sys/menuTree";
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Model model){
		List<Menu> menus = menuService.searchAllExcludeRoot();
		model.addAttribute("list", menus);
		return "modules/sys/menuList";
	}
	
}
