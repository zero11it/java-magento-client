package com.github.chen0040.magento.models.search;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
public class SearchCriteria {
	private Map<String, List<Filter>> filter_groups = new HashMap<>();
	private Integer current_page = null;
	private Integer page_size = null;
	private List<SortOrder> sort_orders = new ArrayList<>();
	
	public SearchCriteria addFilterGroup(String field, String value, ConditionType condition_type) {
		if (!filter_groups.containsKey(field)) {
			filter_groups.put(field, new ArrayList<>());
		}
		
		List<Filter> filter_group = filter_groups.get(field);
		filter_group.add(new Filter(value, condition_type));
		
		return this;
	}
	
	public SearchCriteria add_OR_FilterGroup(String field, String value, ConditionType condition_type) {
		return addFilterGroup(field, "%25" + value + "%25", condition_type);
	}
	
	public SearchCriteria add_AND_FilterGroup(String field, String value, ConditionType condition_type) {
		return addFilterGroup(field, value, condition_type);
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
		return string.replaceAll("[\\s]+", "+");
	}
	
	@Override
	public String toString() {
		List<String> args = new ArrayList<>();
		
		args.addAll(getFilterGroupArgs());
		
		if (current_page != null) {
			args.add("searchCriteria[currentPage]=" + encode(current_page.toString()));
		}
		
		if (page_size != null) {
			args.add("searchCriteria[pageSize]=" + encode(page_size.toString()));
		}
		
		args.addAll(getSortOrderArgs());
		
		return String.join("&", args);
	}

	private List<String> getFilterGroupArgs() {
		if (filter_groups.size() == 0) {
			return new ArrayList<>();
		}
		
		List<String> args = new ArrayList<>();
		
		Set<String> fields = filter_groups.keySet();
		int curField = 0;
		
		for (String field : fields) {
			List<Filter> filters = filter_groups.get(field);
			
			for (int curFilter = 0; curFilter < filters.size(); curFilter++) {
				Filter filter = filters.get(curFilter);
				
				args.add(
						String.format("searchCriteria[filter_groups][%d][filters][%d][field]=%s", curField, curFilter, encode(field))
				);
				args.add(
						String.format("searchCriteria[filter_groups][%d][filters][%d][value]=%s", curField, curFilter, encode(filter.getValue()))
				);
				args.add(
						String.format("searchCriteria[filter_groups][%d][filters][%d][condition_type]=%s", curField, curFilter, encode(filter.getCondition_type().toString()))
				);
			}
		}
		
		return args;
	}

	private List<String> getSortOrderArgs() {
		if (sort_orders.size() == 0) {
			return new ArrayList<>();
		}
		
		List<String> args = new ArrayList<>();
		
		for (int curSortOrder = 0; curSortOrder < sort_orders.size(); curSortOrder++) {
			SortOrder sort_order = sort_orders.get(curSortOrder);
			
			args.add(
					String.format("searchCriteria[sortOrders][%d][field]=%s", curSortOrder, encode(sort_order.getField()))
			);
			args.add(
					String.format("searchCriteria[sortOrders][%d][direction]=%s", curSortOrder, encode(sort_order.getDirection().name()))
			);
		}
		
		return args;
	}
}
