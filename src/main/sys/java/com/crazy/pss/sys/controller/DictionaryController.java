package com.crazy.pss.sys.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crazy.pss.common.persistence.FilterRules;
import com.crazy.pss.common.utils.Constants;
import com.crazy.pss.common.web.BaseController;
import com.crazy.pss.sys.model.Dictionary;
import com.crazy.pss.sys.service.DictionaryService;

/**
 * 
 * @Description 数据字典Controller
 * 
 * @author crazy/Y
 * @date 2013-4-8
 */
@Controller("sys.dictionaryController")
@RequestMapping(value = Constants.ADMIN_PATH+"/sys/dictionary")
public class DictionaryController extends BaseController{

	@Resource(name = "sys.dictionaryService")
	private DictionaryService dictionaryService;
	
	@ModelAttribute("dictionary")
	public Dictionary get(@RequestParam(required=false) String id) {
		if (!StringUtils.isEmpty(id)){
			return dictionaryService.get(id);
		} else {
			return new Dictionary();
		}
	}
	
	@RequestMapping(value = "save")
	public String save(Model model, Dictionary dictionary, RedirectAttributes redirectAttributes) {
		if(!beanValidator(model, dictionary)) {
			return form(model, dictionary);
		}
		dictionaryService.save(dictionary);
		addMessage(redirectAttributes, "保存数据字典" + dictionary.getLabel() + "成功");
		return "redirect:"+Constants.ADMIN_PATH+"/sys/dictionary/list";
	}
	
	@RequestMapping(value = "form")
	public String form(Model model, Dictionary dictionary) {
		model.addAttribute("dictionary", dictionary);
		return "modules/sys/dictionaryForm";
	}
	
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		dictionaryService.delete(id);
		addMessage(redirectAttributes, "删除字典成功");
		return "redirect:"+Constants.ADMIN_PATH+"/sys/dictionary/list";
	}
	
	@RequestMapping(value = {"list", "" })
	public String list(Model model, FilterRules filterRules,
		@RequestParam(value = "pageNo", required = false) Integer pageNo,
		@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		
		Page<Dictionary> page = dictionaryService.searchPage(filterRules.getRules(), pageNo, pageSize, new Sort("type", "sort"));
		model.addAttribute("page", page);
		return "modules/sys/dictionaryList";
	}
	
	@ModelAttribute("filterRules")
	public FilterRules get(FilterRules filterRules) {
		if (filterRules != null){
			return filterRules;
		}else{
			return new FilterRules();
		}
	}
}
