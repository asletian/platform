/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.crazy.pss.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.common.web.BaseController;


/**
 * 
 * @Description 标签Controller
 * 
 * @author crazy/Y
 * @date 2013-5-14
 */
@Controller
@RequestMapping(value = Constants.ADMIN_PATH+"/tags")
public class TagController extends BaseController {
	
	/**
	 * 树结构选择标签(tree.tag)
	 */
	@RequestMapping(value = "tree")
	public String treeselect(Model model,
			@RequestParam(value="url", required=true) String url,
			@RequestParam(value="module", required=true) String module,
			@RequestParam(value="extId", required=false) String extId,
			@RequestParam(value="checked", required=false) String checked,
			@RequestParam(value="selectIds", required=false) String selectIds) {
		
		model.addAttribute("url", url); 	// 树结构数据URL
		model.addAttribute("extId", extId); // 排除的编号ID
		model.addAttribute("checked", checked); // 是否可复选
		model.addAttribute("selectIds", selectIds); // 指定默认选中的ID
		model.addAttribute("module", module);	// 过滤栏目模型（仅针对CMS的Category树）
		return "tags/tree";
	}
	
}
