package com.crazy.pss.common.persistence;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;

public class DynamicSpecifications {
	
	public static <T> Specification<T> bySearchRule(final Collection<FilterRule> rules, final Class<T> clazz) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (rules != null && rules.size() > 0) {

					List<Predicate> predicates = Lists.newArrayList();
					for (FilterRule rule : rules) {
						if(rule.isNull()) continue;
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = StringUtils.split(rule.getName(), ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// logic operator
						switch (rule.getOperator()) {
						case EQ :
							predicates.add(cb.equal(expression, rule.getValue()));
							break;
						case LIKE :
							predicates.add(cb.like(expression, "%" + rule.getValue() + "%"));
							break;
						case GT :
							predicates.add(cb.greaterThan(expression, (Comparable) rule.getValue()));
							break;
						case LT :
							predicates.add(cb.lessThan(expression, (Comparable) rule.getValue()));
							break;
						case GTE :
							predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) rule.getValue()));
							break;
						case LTE :
							predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) rule.getValue()));
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return cb.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return cb.conjunction();
			}
		};
	}
	
	public static <T> Specification<T> bySearchGroup(final FilterGroup group, final Class<T> clazz) {
		return new Specification<T>() {
			
			private Predicate recursion(FilterGroup g, Root<T> root, CriteriaBuilder cb) {
				if(g.getChildren() != null && g.getChildren().size() > 0) {
					switch (g.getOperator()) {
					case AND :
						return cb.and(recursion(g, root, cb));
					case OR :
						return cb.or(recursion(g, root, cb));
					}
				} else {
					Collection<FilterRule> rules = g.getRules();
					if (rules != null && rules.size() > 0) {

						List<Predicate> predicates = Lists.newArrayList();
						for (FilterRule rule : rules) {
							// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
							String[] names = StringUtils.split(rule.getName(), ".");
							Path expression = root.get(names[0]);
							for (int i = 1; i < names.length; i++) {
								expression = expression.get(names[i]);
							}

							// logic operator
							switch (rule.getOperator()) {
							case EQ :
								predicates.add(cb.equal(expression, rule.getValue()));
								break;
							case LIKE :
								predicates.add(cb.like(expression, "%" + rule.getValue() + "%"));
								break;
							case GT :
								predicates.add(cb.greaterThan(expression, (Comparable) rule.getValue()));
								break;
							case LT :
								predicates.add(cb.lessThan(expression, (Comparable) rule.getValue()));
								break;
							case GTE :
								predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) rule.getValue()));
								break;
							case LTE :
								predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) rule.getValue()));
								break;
							}
						}

						if (predicates.size() > 0) {
							switch (g.getOperator()) {
							case AND :
								return cb.and(predicates.toArray(new Predicate[predicates.size()]));
							case OR :
								return cb.or(predicates.toArray(new Predicate[predicates.size()]));
							}
						}
					}
				}
				return cb.conjunction();
			}

			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return recursion(group, root, cb);
			}
		};
	}
	
}
