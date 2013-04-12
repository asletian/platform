package com.crazy.pss.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.common.web.BaseController;
import com.crazy.pss.sys.model.Dictionary;
import com.crazy.pss.sys.service.DictionaryService;

/**
 * 
 * @Description 数据字典
 * 
 * @author crazy/Y
 * @date 2013-4-8
 */
@Controller("sys.dictionaryController")
@RequestMapping(value = Constants.ADMIN_PATH+"/sys/dictionary")
public class DictionaryController extends BaseController{

	@Resource(name = "sys.dictionaryService")
	private DictionaryService dictionaryService;
	
	@ModelAttribute
	public Dictionary get(@RequestParam(required=false) String id) {
		if (!StringUtils.isEmpty(id)){
			return dictionaryService.get(id);
		}else{
			return new Dictionary();
		}
	}
	
	@RequestMapping(value = "list")
	public String list(Dictionary dict, Model model, 
			@RequestParam(value = "pageNo", required = true) Integer pageNo,
			@RequestParam(value = "size", required = true) Integer size){
		
		List<Dictionary> dicts = dictionaryService.search(dict, pageNo, size);
		model.addAttribute("dicts", dicts);
		return "modules/sys/menuList";
	}
}
