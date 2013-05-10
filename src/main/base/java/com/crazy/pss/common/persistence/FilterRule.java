package com.crazy.pss.common.persistence;

import org.springframework.util.StringUtils;

public class FilterRule {
	
	public enum RuleOperator {
		EQ, LIKE, GT, LT, GTE, LTE
	}

	private String name;
	
	private Object value;
	
	private RuleOperator operator = RuleOperator.EQ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public RuleOperator getOperator() {
		return operator;
	}

	public void setOperator(RuleOperator operator) {
		this.operator = operator;
	}

	public boolean isNull() {
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(value) || this.operator == null) {
			return true;
		} else {
			return false;
		}
	}
}
