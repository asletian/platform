package com.crazy.pss.common.persistence;

import java.util.List;

public class FilterGroup {
	
	public enum GroupOperator {
		AND, OR
	}

	private List<FilterRule> rules;
	
	private List<FilterGroup> children;
	
	private GroupOperator operator = GroupOperator.AND;

	public List<FilterRule> getRules() {
		return rules;
	}

	public void setRules(List<FilterRule> rules) {
		this.rules = rules;
	}

	public List<FilterGroup> getChildren() {
		return children;
	}

	public void setChildren(List<FilterGroup> children) {
		this.children = children;
	}

	public GroupOperator getOperator() {
		return operator;
	}

	public void setOperator(GroupOperator operator) {
		this.operator = operator;
	}
	
}
