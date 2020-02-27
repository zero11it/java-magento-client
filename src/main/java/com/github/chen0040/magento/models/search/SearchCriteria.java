package com.github.chen0040.magento.models.search;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
public class SearchCriteria {
	private List<FilterGroup> filter_groups;
	private Integer current_page;
	private Integer page_size;
	private List<SortOrder> sort_orders;
	private int group_index;
	
	public SearchCriteria() {
		this.filter_groups = new ArrayList<>();
		this.current_page = null;
		this.page_size = null;
		this.sort_orders = new ArrayList<>();
		this.group_index = 0;
	}
	
	public SearchCriteria addFilter(String field, String value, ConditionType condition_type) {
		return addANDFilter(field, value, condition_type);
	}
	
	public SearchCriteria addORFilter(String field, String value, ConditionType condition_type) {
		if (filter_groups.size() == 0) {
			return addANDFilter(field, value, condition_type);
		}
		
		FilterGroup filter_group = filter_groups.get(group_index - 1);
		filter_group.addFilter(field, value, condition_type);
		
		return this;
	}
	
	public SearchCriteria addANDFilter(String field, String value, ConditionType condition_type) {
		FilterGroup filter_group = new FilterGroup(group_index++);
		filter_group.addFilter(field, value, condition_type);
		filter_groups.add(filter_group);
		
		return this;
	}
	
	public SearchCriteria setPage(Integer current_page, Integer page_size) {
		this.current_page = current_page;
		this.page_size = page_size;
		
		return this;
	}
	
	public SearchCriteria addSortOrder(String field, SortingDirection direction) {
		sort_orders.add(new SortOrder(field, direction));
		
		return this;
	}

	private String encode(String string) {
		return string.replaceAll("[\\s]+", "+"); // whitespace -> '+'
	}
	
	@Override
	public String toString() {
		List<String> args = new ArrayList<String>();
		
		if (filter_groups.size() > 0) {
			args.add(processFilterGroups());
		}
		if (current_page != null) {
			args.add(String.format("searchCriteria[current_page]=%d", current_page));
		}
		if (page_size != null) {
			args.add(String.format("searchCriteria[page_size]=%d", page_size));
		}
		if (sort_orders.size() > 0) {
			args.add(processSortOrders());
		}
		
		return String.join("&", args);
	}

	private String processFilterGroups() {
		List<String> args = new ArrayList<>();
		
		for (FilterGroup filter_group : filter_groups) {
			for (Filter filter : filter_group.getFilters()) {
				args.addAll(Arrays.asList(
						String.format("searchCriteria[filter_groups][%d][filters][%d][field]=%s", filter_group.getIndex(), filter.getIndex(), encode(filter.getField())),
						String.format("searchCriteria[filter_groups][%d][filters][%d][value]=%s", filter_group.getIndex(), filter.getIndex(), encode(filter.getValue())),
						String.format("searchCriteria[filter_groups][%d][filters][%d][condition_type]=%s", filter_group.getIndex(), filter.getIndex(), encode(filter.getCondition_type()))
				));
			}
		}
		
		return String.join("&", args);
	}

	private String processSortOrders() {
		List<String> args = new ArrayList<>();
		
		for (int curSortOrder = 0; curSortOrder < sort_orders.size(); curSortOrder++) {
			SortOrder sort_order = sort_orders.get(curSortOrder);
			args.addAll(Arrays.asList(
					String.format("searchCriteria[sortOrders][%d][field]=%s", curSortOrder, encode(sort_order.getField())),
					String.format("searchCriteria[sortOrders][%d][direction]=%s", curSortOrder, encode(sort_order.getDirection().name()))
			));
		}
		
		return String.join("&", args);
	}
}
