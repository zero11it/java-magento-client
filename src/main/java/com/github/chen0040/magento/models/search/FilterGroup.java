package com.github.chen0040.magento.models.search;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class FilterGroup {
	private int index;
	private int filter_index;
	private List<Filter> filters;
	
	public FilterGroup(Integer index) {
		this.index = index;
		this.filter_index = 0;
		this.filters = new ArrayList<>();
	}
	
	public FilterGroup addFilter(String field, String value, ConditionType condition_type) {
		Filter filter = new Filter(field, value, condition_type);
		filter.setIndex(filter_index++);
		filters.add(filter);
		
		return this;
	}
}
