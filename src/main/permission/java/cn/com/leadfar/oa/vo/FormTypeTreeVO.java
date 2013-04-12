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
 * ��VO��Ϊ���ܹ���ҳ������ʾjsTree�����������������ġ�
 * ����jsTree����JSON��ʽ����Ҫ��������ص����ԡ�
 * 
 * �Ա������й���ʱ�õ�
 * @author Lee
 *
 */
public class FormTypeTreeVO {
	
	//�ڵ���ʾ������
	private String data;
	
	//<li>�ڵ��ϵ���������
	private Map attr = new HashMap();
	
	//�ӽڵ�
	private List children = new ArrayList();
	
	public FormTypeTreeVO(FormType type){

		this.data = type.getName();
		
		//���ڵ��������
		attr.put("id", type.getId()); //���������Ǳ���ģ�������ˢ�µ�ʱ�򣬱���ס��״̬�Ĺؼ���������
		
		//������
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
