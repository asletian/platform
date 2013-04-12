package cn.com.leadfar.oa.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.leadfar.oa.model.Form;
import cn.com.leadfar.oa.model.FormType;

/**
 * 本VO是为了能够在页面上显示jsTree表单类型树而量身定做的。
 * 根据jsTree对于JSON格式串的要求，设置相关的属性。
 * 
 * 对表单树进行管理时用到
 * @author Lee
 *
 */
public class FormTypeTreeVO {
	
	//节点显示的名称
	private String data;
	
	//<li>节点上的隐藏属性
	private Map attr = new HashMap();
	
	//子节点
	private List children = new ArrayList();
	
	public FormTypeTreeVO(FormType type){

		this.data = type.getName();
		
		//给节点添加属性
		attr.put("id", type.getId()); //【此属性是必须的！！！在刷新的时候，保持住打开状态的关键！！！】
		
		//子类型
		Set<FormType> typeChildren = type.getChildren();
		if(typeChildren != null)
		for (Iterator iterator = typeChildren.iterator(); iterator.hasNext();) {
			FormType subtype = (FormType) iterator.next();
			FormTypeTreeVO ftv = new FormTypeTreeVO(subtype);
			children.add(ftv);
		}
			
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}

	public Map getAttr() {
		return attr;
	}

	public void setAttr(Map attr) {
		this.attr = attr;
	}
}
